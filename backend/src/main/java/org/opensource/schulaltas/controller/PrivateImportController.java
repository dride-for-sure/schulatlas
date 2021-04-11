package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.controller.model.SchoolImportDto;
import org.opensource.schulaltas.service.SchoolService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/auth/v1/import")
public class PrivateImportController {

 private final SchoolService schoolService;

 public PrivateImportController (SchoolService schoolService) {
  this.schoolService = schoolService;
 }

 @PostMapping
 public void importSchools (@RequestBody List<SchoolImportDto> schoolImportDtos) {
  schoolService.importSchools( schoolImportDtos );
 }
}
