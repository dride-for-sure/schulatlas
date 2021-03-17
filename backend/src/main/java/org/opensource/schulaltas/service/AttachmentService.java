package org.opensource.schulaltas.service;

import org.opensource.schulaltas.model.page.Attachment;
import org.opensource.schulaltas.model.page.enums.AttachmentType;
import org.opensource.schulaltas.repository.AttachmentDb;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

 private final AttachmentDb attachmentDb;

 public AttachmentService (AttachmentDb attachmentDb) {
  this.attachmentDb = attachmentDb;
 }

 public List<Attachment> listAttachments () {
  return attachmentDb.findAll();
 }

 public Optional<Attachment> getAttachment (String fileName) {
  return attachmentDb.findById( fileName );
 }

 public Attachment addAttachment (MultipartFile file) {

  // TODO: Save file on S3
  String fileName = "";
  String bucketName = "";
  String endpointUrl = "";
  String fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;

  Attachment attachment = Attachment.builder()
                                  .fileName( fileName )
                                  .url( fileUrl )
                                  .type( AttachmentType.valueOf( file.getContentType() ) )
                                  .build();
  attachmentDb.save( attachment );
  return attachment;
 }

 public void deleteAttachment (String fileName) {
  Optional<Attachment> attachment = attachmentDb.findById( fileName );
  if ( attachment.isPresent() ) {

   // TODO: Delete files from S3
   attachmentDb.deleteById( fileName );
  }
 }

 public boolean isValidAttachment (MultipartFile file) {
  return !file.getContentType().isEmpty() && List.of( AttachmentType.values() ).contains( file.getContentType() );
 }
}
