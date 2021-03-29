package org.opensource.schulaltas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.controller.model.PageDto;
import org.opensource.schulaltas.model.website.Assembly;
import org.opensource.schulaltas.model.website.Website;
import org.opensource.schulaltas.repository.AssemblyDb;
import org.opensource.schulaltas.repository.PageDb;
import org.opensource.schulaltas.repository.SchoolUserDb;
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
 private PageDb pageDb;

 @Autowired
 private SchoolUserDb schoolUserDb;

 @Autowired
 private PasswordEncoder encoder;

 @BeforeEach
 public void Setup () {
  pageDb.deleteAll();
  pageDb.save( getPage( "A" ) );
  pageDb.save( getPage( "B" ) );
 }

 private String getUrl () {
  return "http://localhost:" + port;
 }

 private Website getPage (String slug) {
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

 private PageDto getPageDto (String slug) {
  return PageDto.builder()
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
 void listPages () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<Website[]> actual = testRestTemplate.exchange( getUrl() + "/auth/v1/page",
          HttpMethod.GET, entity, Website[].class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), arrayContainingInAnyOrder( getPage( "A" ), getPage( "B" ) ) );
 }

 @Test
 @DisplayName ("Get Page should return the specific page")
 void getPageBySlug () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<Website> actual = testRestTemplate.exchange( getUrl() + "/auth/v1/page/slug/A",
          HttpMethod.GET, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getPage( "A" ) ) );
 }

 @Test
 @DisplayName ("Get Page should throw an exception in case of not existing page")
 void getNotExistingPageBySlug () {
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
 void addPage () {
  // GIVEN
  when( timeUTC.now() ).thenReturn( 1L );
  when( assemblyDb.findAll() ).thenReturn( List.of( getAssembly( "A" ), getAssembly( "B" ) ) );
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<PageDto> entity = new HttpEntity<>( getPageDto( "NEW" ), headers );
  ResponseEntity<Website> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/",
                  HttpMethod.POST, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getPage( "NEW" ) ) );
  assertTrue( pageDb.existsById( "NEW" ) );
 }

 @Test
 @DisplayName ("Add existing page should return the existing one")
 void addExistingPage () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<PageDto> entity = new HttpEntity<>( getPageDto( "A" ), headers );
  ResponseEntity<Website> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/",
                  HttpMethod.POST, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getPage( "A" ) ) );
 }

 @Test
 @DisplayName ("Update existing page should return the updated version")
 void updatePage () {
  // GIVEN
  when( timeUTC.now() ).thenReturn( 1L );
  when( assemblyDb.findAll() ).thenReturn( List.of( getAssembly( "A" ), getAssembly( "B" ) ) );

  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<PageDto> entity =
          new HttpEntity<>( getPageDto( "A" ).toBuilder().userId( "UPDATED" ).build(),
                  headers );
  ResponseEntity<Website> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/slug/A",
                  HttpMethod.PUT, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getPage( "A" ).toBuilder().userId( "UPDATED" ).build() ) );
 }

 @Test
 @DisplayName ("Update not existing page should throw an exception")
 void updateNotExistingPage () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<PageDto> entity =
          new HttpEntity<>( getPageDto( "NOTEXISTING" ),
                  headers );
  ResponseEntity<Website> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/slug/NOTEXISTING",
                  HttpMethod.PUT, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }

 @Test
 @DisplayName ("Update existing pages slug should return the updated version")
 void updateSlugOfExistingPage () {
  // GIVEN
  when( timeUTC.now() ).thenReturn( 1L );
  when( assemblyDb.findAll() ).thenReturn( List.of( getAssembly( "A" ), getAssembly( "B" ) ) );

  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<PageDto> entity =
          new HttpEntity<>( getPageDto( "NEWSLUG" ).toBuilder().userId( "UPDATED" ).build(),
                  headers );
  ResponseEntity<Website> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/slug/A",
                  HttpMethod.PUT, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getPage( "NEWSLUG" ).toBuilder().userId( "UPDATED" ).build() ) );
  assertFalse( pageDb.existsById( "A" ) );
 }

 @Test
 @DisplayName ("Update not existing pages slug should throw an exception")
 void updateSlugOfNotExistingPage () {
  // GIVEN
  when( timeUTC.now() ).thenReturn( 1L );
  when( assemblyDb.findAll() ).thenReturn( List.of( getAssembly( "A" ), getAssembly( "B" ) ) );

  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<PageDto> entity =
          new HttpEntity<>( getPageDto( "NEWSLUG" ).toBuilder().userId( "UPDATED" ).build(),
                  headers );
  ResponseEntity<Website> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/slug/NOTEXISTING",
                  HttpMethod.PUT, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
  assertFalse( pageDb.existsById( "NEWSLUG" ) );
 }

 @Test
 @DisplayName ("Set landing page should change landingPage to true and save it within the db")
 void setLandingPageBySlug () {
  // GIVEN
  pageDb.save( getPage( "B" ).toBuilder().landingPage( true ).build() );

  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Website> entity = new HttpEntity<>( headers );
  ResponseEntity<Website> actual = testRestTemplate.exchange(
          getUrl() + "/auth/v1/page/slug/A/landingpage", HttpMethod.PUT, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  assertThat( pageDb.findByLandingPageIs( true ),
          containsInAnyOrder( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  assertThat( pageDb.findByLandingPageIs( false ), containsInAnyOrder( getPage( "B" ) ) );
 }

 @Test
 @DisplayName ("Set landing page again should return it again")
 void setLandingPageAgainAsLandingPageBySlug () {
  // GIVEN
  pageDb.save( getPage( "A" ).toBuilder().landingPage( true ).build() );

  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Website> entity = new HttpEntity<>( headers );
  ResponseEntity<Website> actual = testRestTemplate.exchange(
          getUrl() + "/auth/v1/page/slug/A/landingpage", HttpMethod.PUT, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  assertThat( pageDb.findByLandingPageIs( true ),
          containsInAnyOrder( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  assertThat( pageDb.findByLandingPageIs( false ), containsInAnyOrder( getPage( "B" ) ) );
 }

 @Test
 @DisplayName ("Set not existing landing page should throw an exception")
 void setNotExistingPageAsLandingPageBySlug () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Website> entity = new HttpEntity<>( headers );
  ResponseEntity<Website> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/slug/NOTEXISTING/landingpage",
                  HttpMethod.PUT, entity, Website.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
  assertFalse( pageDb.existsById( "NOTEXISTING" ) );
 }

 @Test
 void deletePageBySlug () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<Void> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/page/slug/A",
                  HttpMethod.DELETE, entity, Void.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertFalse( pageDb.existsById( "A" ) );
 }
}