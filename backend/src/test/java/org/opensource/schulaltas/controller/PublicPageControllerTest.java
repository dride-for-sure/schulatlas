package org.opensource.schulaltas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.page.Assembly;
import org.opensource.schulaltas.model.page.Page;
import org.opensource.schulaltas.repository.PageDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.is;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PublicPageControllerTest {

 @LocalServerPort
 private int port;

 @Autowired
 private TestRestTemplate testRestTemplate;

 @Autowired
 private PageDb pageDb;

 @BeforeEach
 public void Setup () {
  pageDb.deleteAll();
  pageDb.save( getPage( "page1" ) );
  pageDb.save( getPage( "page2" ) );
 }

 private String getUrl () {
  return "http://localhost:" + port + "/api/v1/page";
 }

 private Page getPage (String name) {
  Map<String, List<Object>> components = new HashMap<>();
  components.put( "Header", List.of() );
  return Page.builder()
                 .name( name )
                 .updated( 1L )
                 .userId( "1" )
                 .assemblies( List.of(
                         Assembly.builder()
                                 .type( "Header" )
                                 .components( List.of() )
                                 .build() ) ).build();
 }

 @Test
 @DisplayName ("List pages should return all pages within the db")
 void listPages () {
  // WHEN
  ResponseEntity<Page[]> response = testRestTemplate.getForEntity( getUrl(), Page[].class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), arrayContainingInAnyOrder( getPage( "page1" ), getPage( "page2" ) ) );
 }

 @Test
 @DisplayName ("Get page should return an specific existing page from the db")
 void getPage () {
  // WHEN
  ResponseEntity<Page> response = testRestTemplate.getForEntity(
          getUrl() + "/name/page1", Page.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), is( getPage( "page1" ) ) );
 }

 @Test
 @DisplayName ("Get page should throw an exception for a not existing page")
 void getNotExistingPage () {
  // WHEN
  ResponseEntity<Page> response = testRestTemplate.getForEntity(
          getUrl() + "/name/XXX", Page.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }
}