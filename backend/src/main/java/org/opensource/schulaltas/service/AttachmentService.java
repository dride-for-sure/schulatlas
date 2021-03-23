package org.opensource.schulaltas.service;

import org.opensource.schulaltas.model.page.Attachment;
import org.opensource.schulaltas.repository.AttachmentDb;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

 private final StorageService storageService;
 private final AttachmentDb attachmentDb;

 public AttachmentService (AttachmentDb attachmentDb, StorageService storageService) {
  this.attachmentDb = attachmentDb;
  this.storageService = storageService;
 }

 public List<Attachment> listAttachments () {
  return attachmentDb.findAll();
 }

 public Optional<Attachment> getAttachmentByFilename (String fileName) {
  return attachmentDb.findById( fileName );
 }

 public Optional<Attachment> addAttachment (MultipartFile file) {
  Optional<Attachment> attachment = storageService.save( file );
  if ( attachment.isPresent() ) {
   attachmentDb.save( attachment.get() );
   return attachment;
  }
  return Optional.empty();
 }

 public void deleteAttachmentByFilename (String fileName) {
  Optional<Attachment> attachment = attachmentDb.findById( fileName );
  if ( attachment.isPresent() ) {
   storageService.deleteFileById( fileName );
   attachmentDb.deleteById( fileName );
  }
 }

 public boolean isValidAttachment (MultipartFile file) {
  List<String> validFileTypes = List.of(
          "image/jpg",
          "image/jpeg",
          "application/pdf"
  );
  return !file.getContentType().isEmpty() && validFileTypes.contains( file.getContentType() );
 }
}
