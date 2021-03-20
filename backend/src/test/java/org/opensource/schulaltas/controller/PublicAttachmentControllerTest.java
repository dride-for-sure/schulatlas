package org.opensource.schulaltas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.page.Attachment;
import org.opensource.schulaltas.repository.AttachmentDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.is;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PublicAttachmentControllerTest {

 @LocalServerPort
 private int port;

 @Autowired
 private TestRestTemplate testRestTemplate;

 @Autowired
 private AttachmentDb attachmentDb;

 @BeforeEach
 public void Setup () {
  attachmentDb.deleteAll();
  attachmentDb.save( getAttachment( "file1" ) );
  attachmentDb.save( getAttachment( "file2" ) );
 }

 private String getUrl () {
  return "http://localhost:" + port + "api/attachment";
 }

 private Attachment getAttachment (String fileName) {
  return Attachment.builder()
                 .fileName( fileName )
                 .type( "image/jpg" )
                 .url( "URL" )
                 .build();
 }

 @Test
 @DisplayName ("List attachments should return a list with all attachments within the db")
 void listAttachments () {
  // WHEN
  ResponseEntity<Attachment[]> response = testRestTemplate.getForEntity( getUrl(), Attachment[].class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( response.getBody(), arrayContainingInAnyOrder(
          getAttachment( "file1" ),
          getAttachment( "file2" ) ) );
 }
}