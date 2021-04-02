package org.opensource.schulaltas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.controller.model.TypeDto;
import org.opensource.schulaltas.model.school.*;
import org.opensource.schulaltas.repository.AvailableTypeDb;
import org.opensource.schulaltas.repository.SchoolDb;
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

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PrivateTypeControllerTest {

 @LocalServerPort
 private int port;

 @Autowired
 private TestRestTemplate testRestTemplate;

 @Autowired
 private SchoolDb schoolDb;

 @Autowired
 private SchoolUserDb schoolUserDb;

 @Autowired
 private AvailableTypeDb availableTypeDb;

 @Autowired
 private PasswordEncoder encoder;

 @BeforeEach
 public void setup () {
  schoolDb.deleteAll();
  schoolDb.save( getSchool( "1" ) );
  schoolDb.save( getSchool( "2" ) );
  availableTypeDb.deleteAll();
  availableTypeDb.save( getAvailableType( "A" ) );
  availableTypeDb.save( getAvailableType( ( "B" ) ) );
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
  ResponseEntity<String> response = testRestTemplate.postForEntity( getUrl() + "/api/v1/login",
          authenticationRequest, String.class );
  return response.getBody();
 }

 private School getSchool (String id) {
  return School.builder()
                 .number( id )
                 .name( "Goetheschule" )
                 .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                 .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                 .coordinates( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() )
                 .updated( 1L )
                 .type( "A" )
                 .userId( "A" )
                 .markedOutdated( 0 )
                 .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                 .build();
 }

 private AvailableType getAvailableType (String name) {
  return AvailableType.builder()
                 .name( name )
                 .build();
 }

 @Test
 @DisplayName ("List types should return all unique types existing within the schoolDb")
 void listTypes () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<TypeDto[]> response = testRestTemplate.exchange(
          getUrl() + "/auth/v1/type/used", HttpMethod.GET, entity, TypeDto[].class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), arrayContainingInAnyOrder(
          TypeDto.builder().name( "A" ).count( 2 ).build()
  ) );
 }

 @Test
 @DisplayName ("List available types should return all available types from the availableTypesDb")
 void listAvailableTypes () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<AvailableType[]> response = testRestTemplate.exchange(
          getUrl() + "/auth/v1/type/available", HttpMethod.GET, entity, AvailableType[].class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), arrayContainingInAnyOrder(
          getAvailableType( "A" ),
          getAvailableType( "B" )
  ) );
 }
}
