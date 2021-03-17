package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.attachment.Attachment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping ("/api")
public class AttachmentController {

 private final AttachmentService attachmentService;

 public AttachmentController (AttachmentService attachmentService) {
  this.attachmentService = attachmentService;
 }

 @GetMapping ("/attachment")
 public List<Attachment> listAttachments () {
  return attachmentService.listAttachments();
 }

 @GetMapping ("/attachment/{id}")
 public Attachment getAttachment (@PathVariable String id) {
  return attachmentService.getAttachment( id )
                 .orElseThrow( () -> new ResponseStatusException(
                         HttpStatus.BAD_REQUEST, "Attachment: " + id + " not available" ) );
 }

 @PostMapping ("/attachment")
 public boolean addAttachment (@RequestParam ("file") MultipartFile file) {
  if ( file.isEmpty() ) {
   throw new ResponseStatusException( HttpStatus.NOT_ACCEPTABLE, "This is not a valid file" );
  }
  if ( attachmentService.isValidAttachment ) {
   attachmentService.addAttachment( file );
  }
  return true;
 }

 @DeleteMapping ("/attachment/{id}")
 public void deleteAttachment (@PathVariable String id) {
  attachmentService.deleteAttachment( id )
          .orElseThrow( () -> new ResponseStatusException(
                  HttpStatus.BAD_REQUEST, "Could not delete attachment: " + id ) );
 }
}
