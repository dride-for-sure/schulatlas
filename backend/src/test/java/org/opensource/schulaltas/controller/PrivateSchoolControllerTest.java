package org.opensource.schulaltas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.controller.model.SchoolDto;
import org.opensource.schulaltas.model.school.*;
import org.opensource.schulaltas.repository.AvailablePropertyDb;
import org.opensource.schulaltas.repository.SchoolDb;
import org.opensource.schulaltas.repository.SchoolUserDb;
import org.opensource.schulaltas.security.model.AuthenticationRequest;
import org.opensource.schulaltas.security.model.SchoolUser;
import org.opensource.schulaltas.security.model.enums.SchoolUserAuthorities;
import org.opensource.schulaltas.service.GeoService;
import org.opensource.schulaltas.service.TimeUTC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrivateSchoolControllerTest {

 @LocalServerPort
 private int port;

 @MockBean
 private GeoService geoService;

 @MockBean
 private TimeUTC timeUTC;

 @Autowired
 private TestRestTemplate testRestTemplate;

 @Autowired
 private SchoolDb schoolDb;

 @Autowired
 private AvailablePropertyDb availablePropertyDb;

 @Autowired
 private SchoolUserDb schoolUserDb;

 @Autowired
 private PasswordEncoder encoder;

 @BeforeEach
 public void Setup () {
  schoolDb.deleteAll();
  schoolDb.save( getSchool( "1" ) );
  schoolDb.save( getSchool( "2" ) );
  availablePropertyDb.deleteAll();
  availablePropertyDb.save( AvailableProperty.builder().name( "A" ).unit( "C" ).build() );
 }

 private School getSchool (String number) {
  return School.builder()
                 .number( number )
                 .name( "Goetheschule" )
                 .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                 .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                 .coordinates( Coordinates.builder().longitude( 1.1 ).latitude( 1.1 ).build() )
                 .updated( 1L )
                 .userId( "A" )
                 .markedOutdated( 0 )
                 .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                 .build();
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

 @Test
 @DisplayName ("List schools should return a list of schools")
 void listSchools () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<School[]> response = testRestTemplate.exchange( getUrl() + "/auth/v1/school",
          HttpMethod.GET, entity, School[].class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), arrayContainingInAnyOrder( getSchool( "1" ), getSchool( "2" ) ) );
 }

 @Test
 @DisplayName ("Get school should return the specific school")
 void getSchoolByNumber () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<School> response = testRestTemplate.exchange(
          getUrl() + "/auth/v1/school/number/1", HttpMethod.GET, entity, School.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), is( getSchool( "1" ) ) );
 }

 @Test
 @DisplayName ("Get invalid school should throw an exception")
 void getInvalidSchoolByNumber () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<School> response = testRestTemplate.exchange(
          getUrl() + "/auth/v1/school/number/3", HttpMethod.GET, entity, School.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }

 @Test
 @DisplayName ("Add school should add the school to the db and return the school again")
 void addSchool () {
  // WHEN
  SchoolDto schoolDto = SchoolDto.builder()
                                .number( "100" )
                                .name( "Goetheschule" )
                                .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                                .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                                .userId( "A" )
                                .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                                .build();
  when( timeUTC.now() ).thenReturn( 1L );
  when( geoService.getCoordinatesFromAddress( schoolDto.getAddress() ) )
          .thenReturn( Optional.of( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ) );

  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<SchoolDto> entity = new HttpEntity<>( schoolDto, headers );
  ResponseEntity<School> response = testRestTemplate.exchange(
          getUrl() + "/auth/v1/school", HttpMethod.POST, entity, School.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), is( getSchool( "100" ) ) );
  assertTrue( schoolDb.existsById( "100" ) );
 }

 @Test
 @DisplayName ("Add existing school should return existing school rather than add school again")
 void addExistingSchool () {
  // WHEN
  SchoolDto schoolDto = SchoolDto.builder()
                                .number( "1" )
                                .name( "Goetheschule" )
                                .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                                .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                                .userId( "A" )
                                .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                                .build();
  when( timeUTC.now() ).thenReturn( 1L );
  when( geoService.getCoordinatesFromAddress( schoolDto.getAddress() ) )
          .thenReturn( Optional.of( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ) );

  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<SchoolDto> entity = new HttpEntity<>( schoolDto, headers );
  ResponseEntity<School> response = testRestTemplate.exchange(
          getUrl() + "/auth/v1/school", HttpMethod.POST, entity, School.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), is( getSchool( "1" ) ) );
 }

 @Test
 @DisplayName ("Add non existing invalid school should throw an exception")
 void addInvalidSchool () {
  // WHEN
  SchoolDto schoolDto = SchoolDto.builder()
                                .number( "NEW SCHOOL NUMBER" )
                                .name( "Goetheschule" )
                                .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                                .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                                .userId( "A" )
                                .properties( List.of( Property.builder().name( "INVALID PROPERTY" ).value( "B" ).unit( "C" ).build() ) )
                                .build();
  when( timeUTC.now() ).thenReturn( 1L );
  when( geoService.getCoordinatesFromAddress( schoolDto.getAddress() ) )
          .thenReturn( Optional.of( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ) );

  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<SchoolDto> entity = new HttpEntity<>( schoolDto, headers );
  ResponseEntity<School> response = testRestTemplate.exchange(
          getUrl() + "/auth/v1/school", HttpMethod.POST, entity, School.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }

 @Test
 @DisplayName ("Update existing school should save it within the db and return again")
 void updateSchool () {
  // WHEN
  SchoolDto schoolDto = SchoolDto.builder()
                                .number( "1" )
                                .name( "Goetheschule" )
                                .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                                .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                                .userId( "A" )
                                .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                                .build();
  when( timeUTC.now() ).thenReturn( 1L );
  when( geoService.getCoordinatesFromAddress( schoolDto.getAddress() ) )
          .thenReturn( Optional.of( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ) );

  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<SchoolDto> entity = new HttpEntity<>( schoolDto, headers );
  ResponseEntity<School> response =
          testRestTemplate.exchange( getUrl() + "/auth/v1/school/number/" + schoolDto.getNumber(),
                  HttpMethod.PUT, entity, School.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), is( getSchool( "1" ) ) );
 }

 @Test
 @DisplayName ("Update non existing school should not save it and throw an exception")
 void updateNotExistingSchool () {
  // WHEN
  SchoolDto schoolDto = SchoolDto.builder()
                                .number( "NOT EXISTING SCHOOL NUMBER" )
                                .name( "Goetheschule" )
                                .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                                .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                                .userId( "A" )
                                .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                                .build();
  when( timeUTC.now() ).thenReturn( 1L );
  when( geoService.getCoordinatesFromAddress( schoolDto.getAddress() ) )
          .thenReturn( Optional.of( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ) );

  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<SchoolDto> entity = new HttpEntity<>( schoolDto, headers );
  ResponseEntity<School> response =
          testRestTemplate.exchange( getUrl() + "/auth/v1/school/number/" + schoolDto.getNumber(),
                  HttpMethod.PUT, entity, School.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }

 @Test
 @DisplayName ("Update school with invalid props should not save it and throw an exception")
 void updateSchoolInvalidProps () {
  // WHEN
  SchoolDto schoolDto = SchoolDto.builder()
                                .number( "1" )
                                .name( "Goetheschule" )
                                .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                                .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                                .userId( "A" )
                                .properties( List.of( Property.builder().name( "INVALID PROPERTY" ).value(
                                        "B" ).unit( "C" ).build() ) )
                                .build();
  when( timeUTC.now() ).thenReturn( 1L );
  when( geoService.getCoordinatesFromAddress( schoolDto.getAddress() ) )
          .thenReturn( Optional.of( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ) );

  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<SchoolDto> entity = new HttpEntity<>( schoolDto, headers );
  ResponseEntity<School> response =
          testRestTemplate.exchange( getUrl() + "/auth/v1/school/number/" + schoolDto.getNumber(),
                  HttpMethod.PUT, entity, School.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }

 @Test
 @DisplayName ("Update school with invalid coordinates should not save it and throw an exception")
 void updateSchoolInvalidCoordinates () {
  // WHEN
  SchoolDto schoolDto = SchoolDto.builder()
                                .number( "1" )
                                .name( "Goetheschule" )
                                .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                                .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                                .userId( "A" )
                                .properties( List.of( Property.builder().name( "A" ).value(
                                        "B" ).unit( "C" ).build() ) )
                                .build();
  when( timeUTC.now() ).thenReturn( 1L );
  when( geoService.getCoordinatesFromAddress( schoolDto.getAddress() ) )
          .thenReturn( Optional.empty() );

  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<SchoolDto> entity = new HttpEntity<>( schoolDto, headers );
  ResponseEntity<School> response =
          testRestTemplate.exchange( getUrl() + "/auth/v1/school/number/" + schoolDto.getNumber(),
                  HttpMethod.PUT, entity, School.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }


 @Test
 @DisplayName ("Delete existing school should delete it from db")
 void deleteSchoolByNumber () {
  // WHEN
  SchoolDto schoolDto = SchoolDto.builder()
                                .number( "1" )
                                .name( "Goetheschule" )
                                .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                                .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                                .userId( "A" )
                                .properties( List.of( Property.builder().name( "INVALID PROPERTY" ).value(
                                        "B" ).unit( "C" ).build() ) )
                                .build();
  when( timeUTC.now() ).thenReturn( 1L );
  when( geoService.getCoordinatesFromAddress( schoolDto.getAddress() ) )
          .thenReturn( Optional.of( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ) );

  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<SchoolDto> entity = new HttpEntity<>( schoolDto, headers );
  ResponseEntity<School> response =
          testRestTemplate.exchange( getUrl() + "/auth/v1/school/number/" + schoolDto.getNumber(),
                  HttpMethod.DELETE, entity, School.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertFalse( schoolDb.existsById( "1" ) );
 }
}