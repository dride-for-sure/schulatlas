package org.opensource.schulaltas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.controller.model.WebsiteDto;
import org.opensource.schulaltas.model.website.Assembly;
import org.opensource.schulaltas.model.website.Website;
import org.opensource.schulaltas.repository.AssemblyDb;
import org.opensource.schulaltas.repository.SchoolUserDb;
import org.opensource.schulaltas.repository.WebsiteDb;
import org.opensource.schulaltas.security.model.AuthenticationRequest;
import org.opensource.schulaltas.security.model.SchoolUser;
import org.opensource.schulaltas.security.model.enums.SchoolUserAuthorities;
import org.opensource.schulaltas.service.TimeUTC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PrivateWebsiteControllerTest {

 @LocalServerPort
 private int port;

 @MockBean
 private TimeUTC timeUTC;

 @MockBean
 private AssemblyDb assemblyDb;

 @Autowired
 private TestRestTemplate testRestTemplate;

 @Autowired
 private WebsiteDb websiteDb;

 @Autowired
 private SchoolUserDb schoolUserDb;

 @Autowired
 private PasswordEncoder encoder;

 @BeforeEach
 public void Setup () {
  websiteDb.deleteAll();
  websiteDb.save( getWebsite( "A" ) );
  websiteDb.save( getWebsite( "B" ) );
 }

 private String getUrl () {
  return "http://localhost:" + port;
 }

 private Website getWebsite (String slug) {
  return Website.builder()
                 .slug( slug )
                 .updated( 1L )
                 .userId( "1" )
                 .landingPage( false )
                 .assemblies( List.of(
                         Assembly.builder()
                                 .type( "A" )
                                 .components( List.of() )
                                 .build() ) ).build();
 }

 private WebsiteDto getWebsiteDto (String slug) {
  return WebsiteDto.builder()
                 .slug( slug )
                 .userId( "1" )
                 .assemblies( List.of(
                         Assembly.builder()
                                 .type( "A" )
                                 .components( List.of() )
                                 .build() ) ).build();
 }

 private Assembly getAssembly (String slug) {
  return Assembly.builder().type( slug ).components( List.of() ).build();
 }

 private String getJWTToken () {
  String encodedPassword = encoder.encode( "testPassword" );
  schoolUserDb.save( SchoolUser.builder()
                             .username( "testUser" )
                             .password( encodedPassword )
                             .authorities( List.of( SchoolUserAuthorities.ADMIN ) )
                             .build() );
  AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                                                        .username( "testUser" )
                                                        .password( "testPassword" )
                                                        .build();
  ResponseEntity<String> response = testRestTemplate.postForEntity( getUrl() + "/api/v1/login",
          authenticationRequest, String.class );
  return response.getBody();
 }

 @Test
 @DisplayName ("List pages should return all pages within the db")
 void listWebsites () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<Website[]> actual = testRestTemplate.exchange( getUrl() + "/auth/v1/page",
          HttpMethod.GET, entity, Website[].class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), arrayContainingInAnyOrder( getWebsite( "A" ), getWebsite( "B" ) ) );
 }

 @Test
 @DisplayName ("Get Page should return the specific page")
 void getWebsiteBySlug () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<Website> actual = testRestTemplate.exchange( getUrl() + "/auth/v1/page/slug/A",
          HttpMethod.GET, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getWebsite( "A" ) ) );
 }

 @Test
 @DisplayName ("Get Page should throw an exception in case of not existing page")
 void getNotExistingWebsiteBySlug () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<Website> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/slug/NOTEXISTING",
                  HttpMethod.GET, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }

 @Test
 @DisplayName ("Add not existing page to the db should return this again")
 void addWebsite () {
  // GIVEN
  when( timeUTC.now() ).thenReturn( 1L );
  when( assemblyDb.findAll() ).thenReturn( List.of( getAssembly( "A" ), getAssembly( "B" ) ) );
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<WebsiteDto> entity = new HttpEntity<>( getWebsiteDto( "NEW" ), headers );
  ResponseEntity<Website> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/",
                  HttpMethod.POST, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getWebsite( "NEW" ) ) );
  assertTrue( websiteDb.existsById( "NEW" ) );
 }

 @Test
 @DisplayName ("Add existing page should return the existing one")
 void addExistingWebsite () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<WebsiteDto> entity = new HttpEntity<>( getWebsiteDto( "A" ), headers );
  ResponseEntity<Website> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/",
                  HttpMethod.POST, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getWebsite( "A" ) ) );
 }

 @Test
 @DisplayName ("Update existing page should return the updated version")
 void updateWebsite () {
  // GIVEN
  when( timeUTC.now() ).thenReturn( 1L );
  when( assemblyDb.findAll() ).thenReturn( List.of( getAssembly( "A" ), getAssembly( "B" ) ) );

  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<WebsiteDto> entity =
          new HttpEntity<>( getWebsiteDto( "A" ).toBuilder().userId( "UPDATED" ).build(),
                  headers );
  ResponseEntity<Website> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/slug/A",
                  HttpMethod.PUT, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getWebsite( "A" ).toBuilder().userId( "UPDATED" ).build() ) );
 }

 @Test
 @DisplayName ("Update not existing page should throw an exception")
 void updateNotExistingWebsite () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<WebsiteDto> entity =
          new HttpEntity<>( getWebsiteDto( "NOTEXISTING" ),
                  headers );
  ResponseEntity<Website> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/slug/NOTEXISTING",
                  HttpMethod.PUT, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }

 @Test
 @DisplayName ("Update existing pages slug should return the updated version")
 void updateSlugOfExistingWebsite () {
  // GIVEN
  when( timeUTC.now() ).thenReturn( 1L );
  when( assemblyDb.findAll() ).thenReturn( List.of( getAssembly( "A" ), getAssembly( "B" ) ) );

  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<WebsiteDto> entity =
          new HttpEntity<>( getWebsiteDto( "NEWSLUG" ).toBuilder().userId( "UPDATED" ).build(),
                  headers );
  ResponseEntity<Website> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/slug/A",
                  HttpMethod.PUT, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getWebsite( "NEWSLUG" ).toBuilder().userId( "UPDATED" ).build() ) );
  assertFalse( websiteDb.existsById( "A" ) );
 }

 @Test
 @DisplayName ("Update not existing pages slug should throw an exception")
 void updateSlugOfNotExistingWebsite () {
  // GIVEN
  when( timeUTC.now() ).thenReturn( 1L );
  when( assemblyDb.findAll() ).thenReturn( List.of( getAssembly( "A" ), getAssembly( "B" ) ) );

  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<WebsiteDto> entity =
          new HttpEntity<>( getWebsiteDto( "NEWSLUG" ).toBuilder().userId( "UPDATED" ).build(),
                  headers );
  ResponseEntity<Website> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/slug/NOTEXISTING",
                  HttpMethod.PUT, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
  assertFalse( websiteDb.existsById( "NEWSLUG" ) );
 }

 @Test
 @DisplayName ("Set landing page should change landingPage to true and save it within the db")
 void setLandingPageBySlug () {
  // GIVEN
  websiteDb.save( getWebsite( "B" ).toBuilder().landingPage( true ).build() );

  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Website> entity = new HttpEntity<>( headers );
  ResponseEntity<Website> actual = testRestTemplate.exchange(
          getUrl() + "/auth/v1/page/slug/A/landingpage", HttpMethod.PUT, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getWebsite( "A" ).toBuilder().landingPage( true ).build() ) );
  assertThat( websiteDb.findByLandingPageIs( true ),
          containsInAnyOrder( getWebsite( "A" ).toBuilder().landingPage( true ).build() ) );
  assertThat( websiteDb.findByLandingPageIs( false ), containsInAnyOrder( getWebsite( "B" ) ) );
 }

 @Test
 @DisplayName ("Set landing page again should return it again")
 void setLandingWebsiteAgainAsLandingPageBySlug () {
  // GIVEN
  websiteDb.save( getWebsite( "A" ).toBuilder().landingPage( true ).build() );

  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Website> entity = new HttpEntity<>( headers );
  ResponseEntity<Website> actual = testRestTemplate.exchange(
          getUrl() + "/auth/v1/page/slug/A/landingpage", HttpMethod.PUT, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getWebsite( "A" ).toBuilder().landingPage( true ).build() ) );
  assertThat( websiteDb.findByLandingPageIs( true ),
          containsInAnyOrder( getWebsite( "A" ).toBuilder().landingPage( true ).build() ) );
  assertThat( websiteDb.findByLandingPageIs( false ), containsInAnyOrder( getWebsite( "B" ) ) );
 }

 @Test
 @DisplayName ("Set not existing landing page should throw an exception")
 void setNotExistingWebsiteAsLandingPageBySlug () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Website> entity = new HttpEntity<>( headers );
  ResponseEntity<Website> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/slug/NOTEXISTING/landingpage",
                  HttpMethod.PUT, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
  assertFalse( websiteDb.existsById( "NOTEXISTING" ) );
 }

 @Test
 void deleteWebsiteBySlug () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<Void> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/slug/A",
                  HttpMethod.DELETE, entity, Void.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertFalse( websiteDb.existsById( "A" ) );
 }
}