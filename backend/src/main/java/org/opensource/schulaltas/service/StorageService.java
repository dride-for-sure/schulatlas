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
  Optional<String> pathOnS3 = awsService.uploadFileToS3( file, true );
  if ( pathOnS3.isPresent() ) {
   String fileUrl = "https://" + awsConfig.getAWSS3AudioBucket() + ".s3.amazonaws.com/" + pathOnS3.get();
   return Optional.of( Attachment.builder()
                               .fileName( pathOnS3.get() )
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
