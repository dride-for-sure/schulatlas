package org.opensource.schulaltas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.config.AWSConfig;
import org.opensource.schulaltas.model.page.Attachment;
import org.opensource.schulaltas.repository.AttachmentDb;
import org.opensource.schulaltas.repository.SchoolUserDb;
import org.opensource.schulaltas.security.model.AuthenticationRequest;
import org.opensource.schulaltas.security.model.SchoolUser;
import org.opensource.schulaltas.security.model.enums.SchoolUserAuthorities;
import org.opensource.schulaltas.service.AWSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrivateAttachmentControllerTest {

 @LocalServerPort
 private int port;

 @Autowired
 private SchoolUserDb schoolUserDb;

 @Autowired
 private PasswordEncoder encoder;

 @Autowired
 private AttachmentDb attachmentDb;

 @Autowired
 private TestRestTemplate testRestTemplate;

 @Autowired
 private AWSConfig awsConfig;

 @MockBean
 private AWSService awsService;

 @BeforeEach
 public void Setup () {
  attachmentDb.deleteAll();
  attachmentDb.save( getAttachment( "A" ) );
  attachmentDb.save( getAttachment( "B" ) );
 }

 private Attachment getAttachment (String name) {
  return Attachment.builder()
                 .fileName( name )
                 .url( "https://" + awsConfig.getAWSS3AudioBucket() + ".s3.amazonaws.com/" + name )
                 .type( "image/jpeg" )
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
 @DisplayName ("List attachments should return all attachments within the db")
 void listAttachments () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<Attachment[]> actual = testRestTemplate.exchange( getUrl() + "/auth/v1/attachment",
          HttpMethod.GET, entity, Attachment[].class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), arrayContainingInAnyOrder(
          getAttachment( "A" ),
          getAttachment( "B" ) ) );
 }

 @Test
 @DisplayName ("Get attachment should return the specific attachment from the db")
 void getAttachmentByFilename () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<Attachment> actual =
          testRestTemplate.exchange( getUrl() + "/auth/v1/attachment/filename/A",
                  HttpMethod.GET, entity, Attachment.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getAttachment( "A" ) ) );
 }

 @Test
 @DisplayName ("Get a not existing attachment should throw an exception")
 void getNotExistingAttachmentByFilename () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );
  ResponseEntity<Attachment> actual = testRestTemplate.exchange(
          getUrl() + "/auth/v1/attachment/filename/NOTEXISTING", HttpMethod.GET, entity,
          Attachment.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }

 @Test
 @DisplayName ("Add a valid file should add it to the db and return the attachment")
 void addAttachment () throws Exception {
  // GIVEN
  when( awsService.uploadFileToS3( any(), eq( true ) ) )
          .thenReturn( Optional.of( "file.jpg" ) );

  // WHEN
  Path tempFile = Files.createTempFile( "file", ".jpg" );
  byte[] content = "SALT".getBytes();
  Files.write( tempFile, content );
  Resource testFile = new FileSystemResource( tempFile.toFile() );

  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  headers.setAccept( List.of( MediaType.APPLICATION_JSON ) );
  headers.setContentType( MediaType.MULTIPART_FORM_DATA );

  MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
  requestMap.add( "file", testFile );
  HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>( requestMap, headers );

  ResponseEntity<Attachment> actual = testRestTemplate.exchange( getUrl() + "/auth/v1/attachment",
          HttpMethod.POST, entity, Attachment.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( actual.getBody(), is( getAttachment( "file.jpg" ) ) );
 }

 @Test
 @DisplayName ("Add an invalid attachment should throw an exception")
 void addEmptyAttachment () throws Exception {
  // WHEN
  Path tempFile = Files.createTempFile( "file", ".INVALID" );
  byte[] content = new byte[ 0 ];
  Files.write( tempFile, content );
  Resource testFile = new FileSystemResource( tempFile.toFile() );

  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  headers.setAccept( List.of( MediaType.APPLICATION_JSON ) );
  headers.setContentType( MediaType.MULTIPART_FORM_DATA );

  MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
  requestMap.add( "file", testFile );
  HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>( requestMap, headers );

  ResponseEntity<Attachment> actual = testRestTemplate.exchange( getUrl() + "/auth/v1/attachment",
          HttpMethod.POST, entity, Attachment.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }

 @Test
 @DisplayName ("Add an invalid attachment should throw an exception")
 void addInvalidAttachment () throws Exception {
  // WHEN
  Path tempFile = Files.createTempFile( "file", ".INVALID" );
  byte[] content = "SALT".getBytes();
  Files.write( tempFile, content );
  Resource testFile = new FileSystemResource( tempFile.toFile() );

  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  headers.setAccept( List.of( MediaType.APPLICATION_JSON ) );
  headers.setContentType( MediaType.MULTIPART_FORM_DATA );

  MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
  requestMap.add( "file", testFile );
  HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>( requestMap, headers );

  ResponseEntity<Attachment> actual = testRestTemplate.exchange( getUrl() + "/auth/v1/attachment",
          HttpMethod.POST, entity, Attachment.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }

 @Test
 void deleteAttachmentByFilename () {
  // WHEN
  HttpHeaders headers = new HttpHeaders();
  headers.setBearerAuth( getJWTToken() );
  HttpEntity<Void> entity = new HttpEntity<>( headers );

  ResponseEntity<Void> actual = testRestTemplate.exchange(
          getUrl() + "/auth/v1/attachment/filename/A", HttpMethod.DELETE, entity, Void.class );

  // THEN
  assertThat( actual.getStatusCode(), is( HttpStatus.OK ) );
  assertFalse( attachmentDb.existsById( "A" ) );
  verify( awsService ).deleteFileFromS3( "A" );
 }
}