package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.page.Attachment;
import org.opensource.schulaltas.service.AttachmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/api/attachment")
public class PublicAttachmentController {

 private final AttachmentService attachmentService;

 public PublicAttachmentController (AttachmentService attachmentService) {
  this.attachmentService = attachmentService;
 }

 public List<Attachment> listAttachments () {
  return attachmentService.listAttachments();
 }

}
