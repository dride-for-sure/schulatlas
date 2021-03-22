package org.opensource.schulaltas.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AWSService {

 private final String awsS3AudioBucket;
 private final AmazonS3 amazonS3;
 private static final Logger logger = LoggerFactory.getLogger( AWSService.class );

 @Autowired
 public AWSService (Region awsRegion,
                    AWSCredentialsProvider awsCredentialsProvider,
                    String awsS3AudioBucket) {
  amazonS3 = AmazonS3ClientBuilder.standard()
                     .withCredentials( awsCredentialsProvider )
                     .withRegion( awsRegion.getName() ).build();
  this.awsS3AudioBucket = awsS3AudioBucket;
 }

 @Async
 public Optional<String> uploadFileToS3 (MultipartFile multipartFile,
                                         boolean enablePublicReadAccess) {
  String fileName = multipartFile.getOriginalFilename();
  String pathOnS3 = UUID.randomUUID().toString() + "/" + fileName;
  try {
   File file = new File( fileName );
   FileOutputStream fileOutputStream = new FileOutputStream( file );
   fileOutputStream.write( multipartFile.getBytes() );
   fileOutputStream.close();

   PutObjectRequest putObjectRequest = new PutObjectRequest( awsS3AudioBucket, pathOnS3, file );

   if ( enablePublicReadAccess ) {
    putObjectRequest.withCannedAcl( CannedAccessControlList.PublicRead );
   }

   amazonS3.putObject( putObjectRequest );
   file.delete();
   return Optional.of( pathOnS3 );

  } catch ( IOException | AmazonServiceException error ) {
   logger.error( error.getMessage() );
   return Optional.empty();
  }
 }

 @Async
 public void deleteFileFromS3 (String fileName) {
  try {
   DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest( awsS3AudioBucket, fileName );
   amazonS3.deleteObject( deleteObjectRequest );

  } catch ( AmazonServiceException error ) {
   logger.error( error.getMessage() );
  }
 }
}
