package org.opensource.schulaltas.service;

import org.opensource.schulaltas.controller.model.SchoolDto;
import org.opensource.schulaltas.model.school.School;
import org.opensource.schulaltas.repository.SchoolDb;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolService {

 private final SchoolDb schoolDb;
 private final GeoService geoService;

 public SchoolService (SchoolDb schoolDb, GeoService geoService) {
  this.schoolDb = schoolDb;
  this.geoService = geoService;
 }

 public List<School> listSchools () {
  return schoolDb.findAll();
 }

 public Optional<School> getSchool (String number) {
  return schoolDb.findById( number );
 }

 public School addSchool (SchoolDto schoolDto) {
  School school = School.builder()
                          .number( schoolDto.getNumber() )
                          .name( schoolDto.getName() )
                          .address( schoolDto.getAddress() )
                          .contact( schoolDto.getContact() )
                          .geo( geoService.getCoordinatesFromAddress( schoolDto.getAddress() ) )
                          .updated( System.currentTimeMillis() )
                          .userId( schoolDto.getUserId() )
                          .markedOutdated( 0 )
                          .properties( schoolDto.getProperties() )
                          .build();
  schoolDb.save( school );
  return school;
 }

 public Optional<School> increaseOutdatedCount (String number) {
  Optional<School> school = schoolDb.findById( number );
  if ( school.isPresent() ) {
   School updatedSchool = school.get()
                                  .toBuilder()
                                  .markedOutdated( school.get().getMarkedOutdated() + 1 )
                                  .build();
   schoolDb.save( updatedSchool );
   return Optional.of( updatedSchool );
  }
  return Optional.empty();
 }

 public Optional<School> updateSchool (SchoolDto schoolDto) {
  Optional<School> schoolToUpdate = schoolDb.findById( schoolDto.getNumber() );
  if ( schoolToUpdate.isPresent() ) {
   School updatedSchool = schoolToUpdate.get()
                                  .toBuilder()
                                  .name( schoolDto.getName() )
                                  .address( schoolDto.getAddress() )
                                  .contact( schoolDto.getContact() )
                                  .geo( geoService.getCoordinatesFromAddress( schoolDto.getAddress() ) )
                                  .userId( schoolDto.getUserId() )
                                  .updated( System.currentTimeMillis() )
                                  .properties( schoolDto.getProperties() )
                                  .build();
   schoolDb.save( updatedSchool );
   return Optional.of( updatedSchool );
  }
  return Optional.empty();
 }

 public void deleteSchool (String number) {
  schoolDb.deleteById( number );
 }
}