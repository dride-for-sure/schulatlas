package org.opensource.schulaltas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.page.Assembly;
import org.opensource.schulaltas.model.page.Page;
import org.opensource.schulaltas.repository.PageDb;
import org.opensource.schulaltas.repository.SchoolUserDb;
import org.opensource.schulaltas.security.model.AuthenticationRequest;
import org.opensource.schulaltas.security.model.SchoolUser;
import org.opensource.schulaltas.security.model.enums.SchoolUserAuthorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrivateLandingPageControllerTest {

 @LocalServerPort
 private int port;

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
 @DisplayName ("Get landing page should return the landing page")
 void getLandingPage () {
  // GIVEN
  pageDb.save( getPage( "A" ).toBuilder().landingPage( true ).build() );

  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Page> entity = new HttpEntity<>( headers );
  ResponseEntity<Page> actual = testRestTemplate.exchange( getUrl() + "/auth/landingpage",
          HttpMethod.GET, entity, Page.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getPage( "A" ).toBuilder().landingPage( true ).build() ) );

 }

 @Test
 @DisplayName ("Set landing page should change landingPage to true and save it within the db")
 void setLandingPage () {
  // GIVEN
  pageDb.save( getPage( "B" ).toBuilder().landingPage( true ).build() );

  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Page> entity = new HttpEntity<>( headers );
  ResponseEntity<Page> actual = testRestTemplate.exchange( getUrl() + "/auth/landingpage/A",
          HttpMethod.PUT, entity, Page.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  assertThat( pageDb.findByLandingPageIs( true ),
          containsInAnyOrder( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  assertThat( pageDb.findByLandingPageIs( false ), containsInAnyOrder( getPage( "B" ) ) );
 }

 @Test
 @DisplayName ("Set landing page again should return it again")
 void setLandingPageAgainAsLandingPage () {
  // GIVEN
  pageDb.save( getPage( "A" ).toBuilder().landingPage( true ).build() );

  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Page> entity = new HttpEntity<>( headers );
  ResponseEntity<Page> actual = testRestTemplate.exchange( getUrl() + "/auth/landingpage/A",
          HttpMethod.PUT, entity, Page.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  assertThat( pageDb.findByLandingPageIs( true ),
          containsInAnyOrder( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  assertThat( pageDb.findByLandingPageIs( false ), containsInAnyOrder( getPage( "B" ) ) );
 }

 @Test
 @DisplayName ("Set not existing landing page should throw an exception")
 void setNotExistingPageAsLandingPage () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Page> entity = new HttpEntity<>( headers );
  ResponseEntity<Page> actual =
          testRestTemplate.exchange( getUrl() + "/auth/landingpage/NOTEXISTING",
                  HttpMethod.PUT, entity, Page.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
  assertFalse( pageDb.existsById( "NOTEXISTING" ) );
 }
}