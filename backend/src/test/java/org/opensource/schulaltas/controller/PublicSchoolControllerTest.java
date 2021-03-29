package org.opensource.schulaltas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.school.*;
import org.opensource.schulaltas.repository.SchoolDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PublicSchoolControllerTest {

 @LocalServerPort
 private int port;

 @Autowired
 private TestRestTemplate testRestTemplate;

 @Autowired
 private SchoolDb schoolDb;

 @BeforeEach
 public void setup () {
  schoolDb.deleteAll();
  schoolDb.save( getSchool( "1" ) );
  schoolDb.save( getSchool( "2" ) );
 }

 private String getUrl () {
  return "http://localhost:" + port + "/api/v1/school";
 }

 private School getSchool (String id) {
  return School.builder()
                 .number( id )
                 .name( "Goetheschule" )
                 .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                 .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                 .coordinates( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() )
                 .updated( 1L )
                 .userId( "A" )
                 .markedOutdated( 0 )
                 .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                 .build();
 }

 @Test
 @DisplayName ("List all schools should return a page of all schools in asc order")
 void listAllSchoolsAsc () {
  // WHEN
  ResponseEntity<RestPage<School>> response = testRestTemplate.exchange(
          getUrl() + "?page=0&size=1000", HttpMethod.GET, null,
          new ParameterizedTypeReference<RestPage<School>>() {} );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody().getContent(), is( List.of( getSchool( "1" ), getSchool( "2" ) ) ) );
 }

 @Test
 @DisplayName ("List schools paginated in desc order should return a page of schools in desc order")
 void listSchoolsPaginatedDesc () {
  // WHEN
  ResponseEntity<RestPage<School>> response = testRestTemplate.exchange(
          getUrl() + "?page=0&size=2&direction=desc", HttpMethod.GET, null,
          new ParameterizedTypeReference<RestPage<School>>() {} );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody().getContent(), is( List.of( getSchool( "2" ), getSchool( "1" ) ) ) );
 }

 @Test
 @DisplayName ("List schools paginated sorted desc by number order should return a page of " +
                       "schools sorted by number in desc order")
 void listSchoolsPaginatedAsc () {
  // WHEN
  ResponseEntity<RestPage<School>> response = testRestTemplate.exchange(
          getUrl() + "?page=0&size=2&direction=desc&sort=number", HttpMethod.GET, null,
          new ParameterizedTypeReference<RestPage<School>>() {} );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody().getContent(), is( List.of( getSchool( "2" ), getSchool( "1" ) ) ) );
 }

 @Test
 @DisplayName ("Get school should return the a school")
 void getSchool () {
  // WHEN
  ResponseEntity<School> response = testRestTemplate.getForEntity(
          getUrl() + "/number/1", School.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), is( getSchool( "1" ) ) );
 }

 @Test
 @DisplayName ("Get invalid school should throw a responseStatusException")
 void getInvalidSchool () {
  // WHEN
  ResponseEntity<School> response = testRestTemplate.getForEntity(
          getUrl() + "/number/3", School.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );

 }

 @Test
 @DisplayName ("Increase outdated count should return the school object with increased outdated count")
 void increaseOutdatedCount () {
  // WHEN
  ResponseEntity<School> response = testRestTemplate.postForEntity(
          getUrl() + "/number/1/outdated", "", School.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(),
          is( getSchool( "1" ).toBuilder().markedOutdated( getSchool( "1" ).getMarkedOutdated() + 1 ).build() ) );
 }

 @Test
 @DisplayName ("Increase outdated count should throw a responseStatusException for an invalid school")
 void increaseOutdatedCountForInvalidSchool () {
  // WHEN
  ResponseEntity<School> response = testRestTemplate.postForEntity(
          getUrl() + "/number/3/outdated", "", School.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }
}