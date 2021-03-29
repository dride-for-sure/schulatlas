package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.website.Attachment;
import org.opensource.schulaltas.service.AttachmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping ("/auth/v1/attachment")
public class PrivateAttachmentController {

 private final AttachmentService attachmentService;

 public PrivateAttachmentController (AttachmentService attachmentService) {
  this.attachmentService = attachmentService;
 }

 @GetMapping
 public List<Attachment> listAttachments () {
  return attachmentService.listAttachments();
 }

 @GetMapping ("/filename/{filename}")
 public Attachment getAttachmentByFilename (@PathVariable String filename) {
  return attachmentService.getAttachmentByFilename( filename )
                 .orElseThrow( () -> new ResponseStatusException(
                         HttpStatus.BAD_REQUEST, "Attachment: " + filename + " not available" ) );
 }

 @PostMapping
 public Attachment addAttachment (@RequestParam ("file") MultipartFile file) {
  if ( file.isEmpty() ) {
   throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "File is empty" );
  }
  if ( attachmentService.isValidAttachment( file ) ) {
   return attachmentService.addAttachment( file )
                  .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                          "Could not save the file" ) );
  }
  throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "This file has no valid fileType" );
 }

 @DeleteMapping ("/filename/{filename}")
 public void deleteAttachmentByFilename (@PathVariable String filename) {
  attachmentService.deleteAttachmentByFilename( filename );
 }
}