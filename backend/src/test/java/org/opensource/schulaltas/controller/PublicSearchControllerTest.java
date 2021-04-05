package org.opensource.schulaltas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.school.*;
import org.opensource.schulaltas.repository.AvailableTypeDb;
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
class PublicSearchControllerTest {

 @LocalServerPort
 private int port;

 @Autowired
 private SchoolDb schoolDb;

 @Autowired
 private AvailableTypeDb availableTypeDb;

 @Autowired
 private TestRestTemplate testRestTemplate;

 @BeforeEach
 public void Setup () {
  schoolDb.deleteAll();
  schoolDb.save( getSchool( "A" ) );
  schoolDb.save( getSchool( "B" ) );
  schoolDb.save( getSchool( "C" ) );
  availableTypeDb.deleteAll();
  availableTypeDb.save( getAvailableType( "AB" ) );
  availableTypeDb.save( getAvailableType( "BB" ) );
  availableTypeDb.save( getAvailableType( "CB" ) );
 }

 private AvailableType getAvailableType (String name) {
  return AvailableType.builder().name( name ).build();
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

 private String getUrl () {
  return "http://localhost:" + port + "/api/v1/search";
 }

 @Test
 @DisplayName ("Search for school should return a page with specific schools, size 2 and order asc")
 void searchForSchoolsAscending () {
  // WHEN
  ResponseEntity<RestPage<School>> response = testRestTemplate.exchange(
          getUrl() + "/school/Goetheschule?page=0&size=2&sort=number&direction=asc",
          HttpMethod.GET, null, new ParameterizedTypeReference<RestPage<School>>() {} );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody().getContent(), is( List.of( getSchool( "A" ), getSchool( "B" ) ) ) );
  assertThat( response.getBody().getTotalElements(), is( 3L ) );
  assertThat( response.getBody().getTotalPages(), is( 2 ) );
 }

 @Test
 @DisplayName ("Search for school should return a page with specific schools, size 2 and order desc")
 void searchForSchoolsDescending () {
  // WHEN
  ResponseEntity<RestPage<School>> response = testRestTemplate.exchange(
          getUrl() + "/school/Goetheschule?page=0&size=2&sort=number&direction=desc",
          HttpMethod.GET, null, new ParameterizedTypeReference<RestPage<School>>() {} );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody().getContent(), is( List.of( getSchool( "C" ),
          getSchool( "B" ) ) ) );
  assertThat( response.getBody().getTotalElements(), is( 3L ) );
  assertThat( response.getBody().getTotalPages(), is( 2 ) );
 }

 @Test
 @DisplayName ("Search for school on page 1 should return page 1 with specific school, size 1 and" +
                       " order asc")
 void searchForSchoolsOnPageTwo () {
  // WHEN
  ResponseEntity<RestPage<School>> response = testRestTemplate.exchange(
          getUrl() + "/school/Goetheschule?page=1&size=2&sort=number&direction=asc",
          HttpMethod.GET, null, new ParameterizedTypeReference<RestPage<School>>() {} );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody().getContent(), is( List.of( getSchool( "C" ) ) ) );
  assertThat( response.getBody().getTotalElements(), is( 3L ) );
  assertThat( response.getBody().getTotalPages(), is( 2 ) );
 }

 @Test
 @DisplayName ("Search for school should return an empty page for no matches")
 void searchForSchoolsWithNoMatches () {
  // WHEN
  ResponseEntity<RestPage<School>> response = testRestTemplate.exchange(
          getUrl() + "/school/XXXX?page=0&size=2&sort=number&direction=asc",
          HttpMethod.GET, null, new ParameterizedTypeReference<RestPage<School>>() {} );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody().getContent().isEmpty(), is( true ) );
 }

 /* ++++++++++ */

 @Test
 @DisplayName ("Search for type should return a page with specific type, size 2 and order asc")
 void searchForTypesAscending () {
  // WHEN
  ResponseEntity<RestPage<AvailableType>> response = testRestTemplate.exchange(
          getUrl() + "/type/B?page=0&size=2&sort=name&direction=asc",
          HttpMethod.GET, null, new ParameterizedTypeReference<RestPage<AvailableType>>() {} );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody().getContent(), is( List.of( getAvailableType( "AB" ),
          getAvailableType( "BB" ) ) ) );
  assertThat( response.getBody().getTotalElements(), is( 3L ) );
  assertThat( response.getBody().getTotalPages(), is( 2 ) );
 }

 @Test
 @DisplayName ("Search for type should return a page with specific type, size 2 and order desc")
 void searchForTypeDescending () {
  // WHEN
  ResponseEntity<RestPage<AvailableType>> response = testRestTemplate.exchange(
          getUrl() + "/type/B?page=0&size=2&sort=name&direction=desc",
          HttpMethod.GET, null, new ParameterizedTypeReference<RestPage<AvailableType>>() {} );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody().getContent(), is( List.of( getAvailableType( "CB" ),
          getAvailableType( "BB" ) ) ) );
  assertThat( response.getBody().getTotalElements(), is( 3L ) );
  assertThat( response.getBody().getTotalPages(), is( 2 ) );
 }

 @Test
 @DisplayName ("Search for type on page 1 should return page 1 with specific type, size 1 and" +
                       " order asc")
 void searchForTypeOnPageTwo () {
  // WHEN
  ResponseEntity<RestPage<AvailableType>> response = testRestTemplate.exchange(
          getUrl() + "/type/B?page=1&size=2&sort=name&direction=asc",
          HttpMethod.GET, null, new ParameterizedTypeReference<RestPage<AvailableType>>() {} );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody().getContent(), is( List.of( getAvailableType( "CB" ) ) ) );
  assertThat( response.getBody().getTotalElements(), is( 3L ) );
  assertThat( response.getBody().getTotalPages(), is( 2 ) );
 }

 @Test
 @DisplayName ("Search for type should return an empty page for no matches")
 void searchForTypeWithNoMatches () {
  // WHEN
  ResponseEntity<RestPage<AvailableType>> response = testRestTemplate.exchange(
          getUrl() + "/type/XXXX?page=0&size=2&sort=name&direction=asc",
          HttpMethod.GET, null, new ParameterizedTypeReference<RestPage<AvailableType>>() {} );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody().getContent().isEmpty(), is( true ) );
 }
}