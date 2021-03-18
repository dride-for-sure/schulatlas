package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.school.School;
import org.opensource.schulaltas.service.SchoolService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping ("/api/school")
public class PublicSchoolController {

 private final SchoolService schoolService;

 public PublicSchoolController (SchoolService schoolService) {
  this.schoolService = schoolService;
 }

 @GetMapping
 public List<School> listSchools () {
  return schoolService.listSchools();
 }

 @GetMapping ("/{number}")
 public School getSchool (@PathVariable String number) {
  return schoolService.getSchool( number )
                 .orElseThrow( () -> new ResponseStatusException(
                         HttpStatus.BAD_REQUEST, "School with number: " + number + " is not available" ) );
 }

 @PostMapping ("/{number}/outdated")
 public School increaseOutdatedCount (@PathVariable String number) {
  return schoolService.increaseOutdatedCount( number )
                 .orElseThrow( () -> new ResponseStatusException(
                         HttpStatus.NOT_ACCEPTABLE,
                         "Could not increase counter for school: " + number ) );
 }
}
