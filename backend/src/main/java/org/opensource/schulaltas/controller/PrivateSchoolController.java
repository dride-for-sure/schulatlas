package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.controller.model.SchoolDto;
import org.opensource.schulaltas.model.school.School;
import org.opensource.schulaltas.service.SchoolService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping ("/auth/v1/school")
public class PrivateSchoolController {

 private final SchoolService schoolService;

 public PrivateSchoolController (SchoolService schoolService) {
  this.schoolService = schoolService;
 }

 @GetMapping
 public Page<School> listSchools (
         @RequestParam (defaultValue = "0") int page,
         @RequestParam (defaultValue = "30") int size,
         @RequestParam (defaultValue = "number") String sort,
         @RequestParam (defaultValue = "asc") String direction) {
  return schoolService.listSchools( page, size, sort, direction );
 }

 @GetMapping ("/type/{type}")
 public Page<School> listSchoolsByType (
         @PathVariable String type,
         @RequestParam (defaultValue = "0") int page,
         @RequestParam (defaultValue = "30") int size,
         @RequestParam (defaultValue = "type") String sort,
         @RequestParam (defaultValue = "asc") String direction) {
  return schoolService.listSchoolsByType( type, page, size, sort, direction );
 }

 @GetMapping ("/number/{number}")
 public School getSchoolByNumber (@PathVariable String number) {
  return schoolService.getSchoolByNumber( number )
                 .orElseThrow( () -> new ResponseStatusException(
                         HttpStatus.BAD_REQUEST, "School with number: " + number + " is not available" ) );
 }

 @PostMapping
 public School addSchool (@RequestBody SchoolDto schoolDto) {
  return schoolService.addSchool( schoolDto )
                 .orElseThrow( () -> new ResponseStatusException(
                         HttpStatus.BAD_REQUEST, "Could not add school with number: " + schoolDto.getNumber() ) );
 }

 @PutMapping ("/number/{number}")
 public School updateSchool (@RequestBody SchoolDto schoolDto) {
  return schoolService.updateSchool( schoolDto )
                 .orElseThrow( () -> new ResponseStatusException(
                         HttpStatus.BAD_REQUEST, "Could not update school: " + schoolDto.getNumber() ) );
 }

 @DeleteMapping ("/number/{number}")
 public void deleteSchoolByNumber (@PathVariable String number) {
  schoolService.deleteSchoolByNumber( number );
 }
}
