package org.opensource.schulaltas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.school.AvailableProperty;
import org.opensource.schulaltas.repository.AvailablePropertyDb;
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
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrivatePropertyControllerTest {

 @LocalServerPort
 private int port;

 @Autowired
 private AvailablePropertyDb availablePropertyDb;

 @Autowired
 private TestRestTemplate testRestTemplate;

 @Autowired
 private SchoolUserDb schoolUserDb;

 @Autowired
 private PasswordEncoder encoder;

 @BeforeEach
 public void Setup () {
  availablePropertyDb.deleteAll();
  availablePropertyDb.save( getAvailableProperty( "A" ) );
  availablePropertyDb.save( getAvailableProperty( "B" ) );
 }

 private AvailableProperty getAvailableProperty (String name) {
  return AvailableProperty.builder().name( name ).unit( name ).build();
 }

 private String getUrl () {
  return "http://localhost:" + port;
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
 @DisplayName ("List properties should return all properties from the db")
 void listProperties () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<AvailableProperty[]> response = testRestTemplate.exchange(
          getUrl() + "/auth/property", HttpMethod.GET, entity, AvailableProperty[].class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), arrayContainingInAnyOrder(
          getAvailableProperty( "A" ),
          getAvailableProperty( "B" ) ) );
 }

 @Test
 @DisplayName ("Add property should save the property within the db and return it")
 void addProperty () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<AvailableProperty> entity = new HttpEntity<>( getAvailableProperty( "NEW" ), headers );
  ResponseEntity<AvailableProperty> response = testRestTemplate.exchange(
          getUrl() + "/auth/property", HttpMethod.POST, entity, AvailableProperty.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), is( getAvailableProperty( "NEW" ) ) );
  assertTrue( availablePropertyDb.existsById( "NEW" ) );
 }

 @Test
 @DisplayName ("Add existing property should not save instead return it")
 void addExistingProperty () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<AvailableProperty> entity = new HttpEntity<>( getAvailableProperty( "A" ), headers );
  ResponseEntity<AvailableProperty> response = testRestTemplate.exchange(
          getUrl() + "/auth/property", HttpMethod.POST, entity, AvailableProperty.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), is( getAvailableProperty( "A" ) ) );
 }

 @Test
 @DisplayName ("Update property should update an existing property within the db")
 void updateProperty () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  AvailableProperty updatedProperty =
          getAvailableProperty( "A" ).toBuilder().unit( "UPDATED" ).build();
  HttpEntity<AvailableProperty> entity = new HttpEntity<>( updatedProperty, headers );
  ResponseEntity<AvailableProperty> response = testRestTemplate.exchange(
          getUrl() + "/auth/property/" + updatedProperty.getName(), HttpMethod.PUT, entity,
          AvailableProperty.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), is( updatedProperty.toBuilder().build() ) );
 }

 @Test
 @DisplayName ("Update not existing property should not save it instead throw an exception")
 void updateNotExistingProperty () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<AvailableProperty> entity = new HttpEntity<>( getAvailableProperty( "NOTEXISTING" ),
          headers );
  ResponseEntity<AvailableProperty> response = testRestTemplate.exchange(
          getUrl() + "/auth/property/" + "NOTEXISTING", HttpMethod.PUT, entity,
          AvailableProperty.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }

 @Test
 @DisplayName ("Delete property should delete the property within the db")
 void deleteProperty () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<Void> response = testRestTemplate.exchange(
          getUrl() + "/auth/property/" + "1", HttpMethod.DELETE, entity, Void.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertFalse( availablePropertyDb.existsById( "1" ) );
 }
}