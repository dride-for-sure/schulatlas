package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.school.AvailableType;
import org.opensource.schulaltas.model.school.School;
import org.opensource.schulaltas.service.SchoolSearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/v1/search")
public class PublicSearchController {

 private final SchoolSearchService schoolSearchService;

 public PublicSearchController (SchoolSearchService schoolSearchService) {
  this.schoolSearchService = schoolSearchService;
 }

 @GetMapping ("/school/{search}")
 public Page<School> searchForSchools (
         @PathVariable String search,
         @RequestParam (defaultValue = "0") int page,
         @RequestParam (defaultValue = "30") int size,
         @RequestParam (defaultValue = "name") String sort,
         @RequestParam (defaultValue = "asc") String direction) {
  return this.schoolSearchService.searchForSchools( search, page, size, sort, direction );
 }

 @GetMapping ("/type/{type}")
 public Page<AvailableType> searchForTypes (
         @PathVariable String type,
         @RequestParam (defaultValue = "0") int page,
         @RequestParam (defaultValue = "30") int size,
         @RequestParam (defaultValue = "type") String sort,
         @RequestParam (defaultValue = "asc") String direction) {
  return this.schoolSearchService.searchForTypes( type, page, size, sort, direction );
 }
}
