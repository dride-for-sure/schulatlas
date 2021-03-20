package org.opensource.schulaltas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.page.Component;
import org.opensource.schulaltas.repository.ComponentDb;
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

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.is;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrivateComponentControllerTest {

 @LocalServerPort
 private int port;

 @Autowired
 private ComponentDb componentDb;

 @Autowired
 private PasswordEncoder encoder;

 @Autowired
 private SchoolUserDb schoolUserDb;

 @Autowired
 private TestRestTemplate testRestTemplate;

 @BeforeEach
 public void Setup () {
  componentDb.deleteAll();
  componentDb.save( getComponent( "A" ) );
  componentDb.save( getComponent( "B" ) );
 }

 private String getUrl () {
  return "http://localhost:" + port;
 }

 private Component getComponent (String name) {
  return Component.builder()
                 .type( name )
                 .components( new HashMap<>() )
                 .build();
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
  ResponseEntity<String> response =
          testRestTemplate.postForEntity( getUrl() + "/authenticate", authenticationRequest,
                  String.class );
  return response.getBody();
 }

 @Test
 @DisplayName ("List components should list all available components from db")
 void listComponents () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<Component[]> response = testRestTemplate.exchange( getUrl() + "/auth/component",
          HttpMethod.GET, entity, Component[].class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), arrayContainingInAnyOrder(
          getComponent( "A" ),
          getComponent(
                  "B" ) ) );
 }
}