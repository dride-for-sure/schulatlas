package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.page.Attachment;
import org.opensource.schulaltas.repository.AttachmentDb;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class AttachmentServiceTest {

 private final AttachmentDb attachmentDb = mock( AttachmentDb.class );
 private final StorageService storageService = mock( StorageService.class );
 private final AttachmentService attachmentService = new AttachmentService( attachmentDb, storageService );

 private Attachment getAttachment (String fileName) {
  return Attachment.builder()
                 .fileName( fileName )
                 .type( "image/jpeg" )
                 .url( "URL" )
                 .build();
 }

 @Test
 @DisplayName ("List attachments should return all available attachments form db")
 void listAttachments () {
  // GIVEN
  when( attachmentDb.findAll() ).thenReturn( List.of( getAttachment( "file1" ),
          getAttachment( "file2" ) ) );

  // WHEN
  List<Attachment> actual = attachmentService.listAttachments();

  // THEN
  assertThat( actual, containsInAnyOrder(
          getAttachment( "file1" ),
          getAttachment( "file2" ) ) );
 }

 @Test
 @DisplayName ("Get attachment should return an existing attachment")
 void getAttachment () {
  // GIVEN
  when( attachmentDb.findById( "file1" ) ).thenReturn( Optional.of( getAttachment( "file1" ) ) );

  // WHEN
  Optional<Attachment> actual = attachmentService.getAttachment( "file1" );

  // THEN
  assertThat( actual.get(), is( getAttachment( "file1" ) ) );
 }

 @Test
 @DisplayName ("Get not existing attachment should return an optional empty")
 void getNotExistingAttachment () {
  // GIVEN
  when( attachmentDb.findById( "file1" ) ).thenReturn( Optional.empty() );

  // WHEN
  Optional<Attachment> actual = attachmentService.getAttachment( "file1" );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
 }

 @Test
 @DisplayName ("Add a file should return an optional of the attachment")
 void addAttachment () throws IOException {
  // GIVEN
  MockMultipartFile mockMultipartFile = new MockMultipartFile( "file",
          "fileName.jpg", "image/jpeg", "some text".getBytes() );
  when( storageService.save( mockMultipartFile ) ).thenReturn( Optional.of( getAttachment( "fileName.jpg" ) ) );

  // WHEN
  Optional<Attachment> actual = attachmentService.addAttachment( mockMultipartFile );

  // THEN
  assertThat( actual.get(), is( getAttachment( "fileName.jpg" ) ) );
 }

 @Test
 @DisplayName ("Add a file should return an optional empty if storage failed")
 void addAttachmentToStorageFailed () {
  // GIVEN
  MockMultipartFile mockMultipartFile = new MockMultipartFile( "file",
          "fileName.jpg", "image/jpeg", "some text".getBytes() );
  when( storageService.save( mockMultipartFile ) ).thenReturn( Optional.empty() );

  // WHEN
  Optional<Attachment> actual = attachmentService.addAttachment( mockMultipartFile );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
 }

 @Test
 @DisplayName ("Delete an existing attachment should delete this from the db")
 void deleteAttachment () {
  // GIVEN
  when( attachmentDb.findById( "A" ) ).thenReturn( Optional.of( getAttachment( "A" ) ) );

  // WHEN
  attachmentService.deleteAttachment( "A" );

  // THEN
  verify( storageService ).deleteFileById( "A" );
  verify( attachmentDb ).deleteById( "A" );
 }

 @Test
 @DisplayName ("Delete not existing attachment should not try to delete from storage and db")
 void deleteInvalidAttachment () {
  // GIVEN
  when( attachmentDb.findById( "A" ) ).thenReturn( Optional.empty() );

  // WHEN
  attachmentService.deleteAttachment( "A" );

  // THEN
  verify( storageService, never() ).deleteFileById( "A" );
  verify( attachmentDb, never() ).deleteById( "A" );
 }

 @Test
 @DisplayName ("Is a valid attachment should return true for a image or pdf")
 void isValidAttachment () {
  // WHEN
  MockMultipartFile mockMultipartFile = new MockMultipartFile( "file",
          "fileName.jpg", "image/jpeg", "some text".getBytes() );
  Boolean actual = attachmentService.isValidAttachment( mockMultipartFile );

  // THEN
  assertThat( actual, is( true ) );
 }

 @Test
 @DisplayName ("Is not a valid attachment should return false for everything except image or pdf")
 void isNotValidAttachment () {
  // WHEN
  MockMultipartFile mockMultipartFile = new MockMultipartFile( "file",
          "fileName.jpg", "UNKOWNFILETYPE", "some text".getBytes() );
  Boolean actual = attachmentService.isValidAttachment( mockMultipartFile );

  // THEN
  assertThat( actual, is( false ) );
 }
}