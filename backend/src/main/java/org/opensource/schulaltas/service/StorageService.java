package org.opensource.schulaltas.service;

import org.opensource.schulaltas.config.AWSConfig;
import org.opensource.schulaltas.model.page.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class StorageService {

 private final AWSService awsService;
 private final AWSConfig awsConfig;

 @Autowired
 public StorageService (AWSService awsService, AWSConfig awsConfig) {
  this.awsService = awsService;
  this.awsConfig = awsConfig;
 }

 public Optional<Attachment> save (MultipartFile file) {
  Optional<String> fileName = awsService.uploadFileToS3( file, true );
  if ( fileName.isPresent() ) {
   String fileUrl = "https://" + awsConfig.getAWSS3AudioBucket() + ".s3.amazonaws.com/" + fileName.get();
   return Optional.of( Attachment.builder()
                               .fileName( fileName.get() )
                               .url( fileUrl )
                               .type( file.getContentType() )
                               .build() );
  }
  return Optional.empty();
 }

 public void deleteFileById (String fileName) {
  awsService.deleteFileFromS3( fileName );
 }
}
