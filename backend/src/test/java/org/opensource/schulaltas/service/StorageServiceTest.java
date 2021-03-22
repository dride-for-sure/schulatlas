package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.config.AWSConfig;
import org.opensource.schulaltas.model.page.Attachment;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class StorageServiceTest {

 private final AWSService awsService = mock( AWSService.class );
 private final AWSConfig awsConfig = new AWSConfig();
 private final StorageService storageService = new StorageService( awsService, awsConfig );

 @Test
 @DisplayName ("Save should return an optional of the attachment if s3 upload works out")
 void save () {
  // GIVEN
  MockMultipartFile mockMultipartFile = new MockMultipartFile( "file",
          "fileName.jpg", "image/jpeg", "some text".getBytes() );
  when( awsService.uploadFileToS3( mockMultipartFile, true ) )
          .thenReturn( Optional.of( "1234/fileName.jpg" ) );

  // WHEN
  Optional<Attachment> actual = storageService.save( mockMultipartFile );

  // THEN
  Attachment expected = Attachment.builder()
                                .fileName( "1234/fileName.jpg" )
                                .url( "https://" + awsConfig.getAWSS3AudioBucket() + ".s3.amazonaws.com/1234/fileName.jpg" )
                                .type( "image/jpeg" )
                                .build();
  assertThat( actual.get(), is( expected ) );
 }

 @Test
 @DisplayName ("Save should return an optional empty in case of s3 upload failure")
 void saveWithS3Failure () {
  // GIVEN
  MockMultipartFile mockMultipartFile = new MockMultipartFile( "file",
          "fileName.jpg", "image/jpeg", "some text".getBytes() );
  when( awsService.uploadFileToS3( mockMultipartFile, true ) )
          .thenReturn( Optional.empty() );

  // WHEN
  Optional<Attachment> actual = storageService.save( mockMultipartFile );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
 }

 @Test
 @DisplayName ("Delete file by id should delete the file on S3")
 void deleteFileById () {
  // WHEN
  storageService.deleteFileById( "A" );

  // THEN
  verify( awsService ).deleteFileFromS3( "A" );
 }
}