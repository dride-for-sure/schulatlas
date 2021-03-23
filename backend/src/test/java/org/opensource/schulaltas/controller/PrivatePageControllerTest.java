package org.opensource.schulaltas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.controller.model.PageDto;
import org.opensource.schulaltas.model.page.Assembly;
import org.opensource.schulaltas.model.page.Page;
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
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PrivatePageControllerTest {

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

 private Page getPage (String name) {
  return Page.builder()
                 .name( name )
                 .updated( 1L )
                 .userId( "1" )
                 .landingPage( false )
                 .assemblies( List.of(
                         Assembly.builder()
                                 .type( "A" )
                                 .components( List.of() )
                                 .build() ) ).build();
 }

 private PageDto getPageDto (String name) {
  return PageDto.builder()
                 .name( name )
                 .userId( "1" )
                 .assemblies( List.of(
                         Assembly.builder()
                                 .type( "A" )
                                 .components( List.of() )
                                 .build() ) ).build();
 }

 private Assembly getAssembly (String name) {
  return Assembly.builder().type( name ).components( List.of() ).build();
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
  ResponseEntity<String> response = testRestTemplate.postForEntity( getUrl() + "/authenticate",
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
  ResponseEntity<Page[]> actual = testRestTemplate.exchange( getUrl() + "/auth/page",
          HttpMethod.GET, entity, Page[].class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), arrayContainingInAnyOrder( getPage( "A" ), getPage( "B" ) ) );
 }

 @Test
 @DisplayName ("Get Page should return the specific page")
 void getPage () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<Page> actual = testRestTemplate.exchange( getUrl() + "/auth/page/" + "A",
          HttpMethod.GET, entity, Page.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getPage( "A" ) ) );
 }

 @Test
 @DisplayName ("Get Page should throw an exception in case of not existing page")
 void getNotExistingPage () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<Page> actual =
          testRestTemplate.exchange( getUrl() + "/auth/page/" + "NOTEXISTING",
                  HttpMethod.GET, entity, Page.class );

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
  ResponseEntity<Page> actual =
          testRestTemplate.exchange( getUrl() + "/auth/page/",
                  HttpMethod.POST, entity, Page.class );

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
  ResponseEntity<Page> actual =
          testRestTemplate.exchange( getUrl() + "/auth/page/",
                  HttpMethod.POST, entity, Page.class );

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
  ResponseEntity<Page> actual =
          testRestTemplate.exchange( getUrl() + "/auth/page/" + "A",
                  HttpMethod.PUT, entity, Page.class );

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
  ResponseEntity<Page> actual =
          testRestTemplate.exchange( getUrl() + "/auth/page/" + "NOTEXISTING",
                  HttpMethod.PUT, entity, Page.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }

 @Test
 void deletePage () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<Void> actual =
          testRestTemplate.exchange( getUrl() + "/auth/page/" + "A",
                  HttpMethod.DELETE, entity, Void.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertFalse( pageDb.existsById( "A" ) );
 }
}