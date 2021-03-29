package org.opensource.schulaltas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.website.Assembly;
import org.opensource.schulaltas.model.website.Website;
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
import static org.hamcrest.Matchers.is;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PublicWebsiteControllerTest {

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

 private Website getPage (String slug) {
  Map<String, List<Object>> components = new HashMap<>();
  components.put( "Header", List.of() );
  return Website.builder()
                 .slug( slug )
                 .updated( 1L )
                 .userId( "1" )
                 .landingPage( false )
                 .assemblies( List.of(
                         Assembly.builder()
                                 .type( "Header" )
                                 .components( List.of() )
                                 .build() ) ).build();
 }

 @Test
 @DisplayName ("Get page should return an specific existing page from the db")
 void getPageBySlug () {
  // WHEN
  ResponseEntity<Website> response = testRestTemplate.getForEntity(
          getUrl() + "/slug/page1", Website.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), is( getPage( "page1" ) ) );
 }

 @Test
 @DisplayName ("Get page should throw an exception for a not existing page")
 void getNotExistingPageBySlug () {
  // WHEN
  ResponseEntity<Website> response = testRestTemplate.getForEntity(
          getUrl() + "/slug/XXX", Website.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }
}