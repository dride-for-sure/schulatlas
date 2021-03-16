package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.controller.model.SchoolDto;
import org.opensource.schulaltas.model.school.School;
import org.opensource.schulaltas.service.SchoolService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping ("/api")
public class SchoolController {

 private final SchoolService schoolService;

 public SchoolController (SchoolService schoolService) {
  this.schoolService = schoolService;
 }

 @GetMapping ("/school")
 public List<School> listSchools () {
  return schoolService.listSchools();
 }

 @GetMapping ("/school/{number}")
 public School getSchool (@PathVariable String number) {
  return schoolService.getSchool( number )
                 .orElseThrow( () -> new ResponseStatusException(
                         HttpStatus.BAD_REQUEST, "School with number: " + number + " is not available" ) );
 }

 @PostMapping ("/school")
 public School addSchool (@RequestBody SchoolDto schoolDto) {
  return schoolService.addSchool( schoolDto )
                 .orElseThrow( () -> new ResponseStatusException(
                         HttpStatus.NOT_ACCEPTABLE, "Could not add school: " + schoolDto.getNumber() ) );
 }

 @PutMapping ("/school")
 public School updateSchool (@RequestBody SchoolDto schoolDto) {
  return schoolService.updateSchool( schoolDto )
                 .orElseThrow( () -> new ResponseStatusException(
                         HttpStatus.BAD_REQUEST, "Could not update school: " + schoolDto.getNumber() ) );
 }

 @DeleteMapping ("/school/{number}")
 public void deleteSchool (@PathVariable String number) {
  schoolService.deleteSchool( number )
          .orElseThrow( () -> new ResponseStatusException(
                  HttpStatus.BAD_REQUEST, "Could not delete school: " + number ) );
 }
}
