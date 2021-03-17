package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.attachment.Attachment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping ("/auth/attachment")
public class PrivateAttachmentController {

 private final AttachmentService attachmentService;

 public PrivateAttachmentController (AttachmentService attachmentService) {
  this.attachmentService = attachmentService;
 }

 @GetMapping
 public List<Attachment> listAttachments () {
  return attachmentService.listAttachments();
 }

 @GetMapping ("/{id}")
 public Attachment getAttachment (@PathVariable String id) {
  return attachmentService.getAttachment( id )
                 .orElseThrow( () -> new ResponseStatusException(
                         HttpStatus.BAD_REQUEST, "Attachment: " + id + " not available" ) );
 }

 @PostMapping
 public boolean addAttachment (@RequestParam ("file") MultipartFile file) {
  if ( file.isEmpty() ) {
   throw new ResponseStatusException( HttpStatus.NOT_ACCEPTABLE, "This is not a valid file" );
  }
  if ( attachmentService.isValidAttachment ) {
   attachmentService.addAttachment( file );
  }
  return true;
 }

 @DeleteMapping ("/{id}")
 public void deleteAttachment (@PathVariable String id) {
  attachmentService.deleteAttachment( id );
 }
}