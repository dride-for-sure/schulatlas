package org.opensource.schulaltas.service;


import org.opensource.schulaltas.controller.model.SchoolDto;
import org.opensource.schulaltas.model.school.GeoObject;
import org.opensource.schulaltas.model.school.School;
import org.opensource.schulaltas.repository.SchoolDb;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolService {

 private final SchoolDb schoolDb;
 private final GeoService geoService;
 private final TimeUtil timeUtil;

 public SchoolService (SchoolDb schoolDb, GeoService geoService, TimeUtil timeUtil) {
  this.schoolDb = schoolDb;
  this.geoService = geoService;
  this.timeUtil = timeUtil;
 }

 public List<School> listSchools () {
  return schoolDb.findAll();
 }

 public Optional<School> getSchool (String number) {
  return schoolDb.findById( number );
 }

 public Optional<School> addSchool (SchoolDto schoolDto) {
  Optional<GeoObject> geo = geoService.getCoordinatesFromAddress( schoolDto.getAddress() );
  Optional<School> school = schoolDb.findById( schoolDto.getNumber() );
  if ( school.isEmpty() ) {
   if ( geo.isPresent() ) {
    School newSchool = School.builder()
                               .number( schoolDto.getNumber() )
                               .name( schoolDto.getName() )
                               .address( schoolDto.getAddress() )
                               .contact( schoolDto.getContact() )
                               .geoObject( geo.get() )
                               .updated( timeUtil.now() )
                               .userId( schoolDto.getUserId() )
                               .markedOutdated( 0 )
                               .properties( schoolDto.getProperties() )
                               .build();
    schoolDb.save( newSchool );
    return Optional.of( newSchool );
   }
   return Optional.empty();
  }
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
  Optional<GeoObject> geo = geoService.getCoordinatesFromAddress( schoolDto.getAddress() );
  Optional<School> schoolToUpdate = schoolDb.findById( schoolDto.getNumber() );
  if ( schoolToUpdate.isPresent() && geo.isPresent() ) {
   School updatedSchool = schoolToUpdate.get()
                                  .toBuilder()
                                  .name( schoolDto.getName() )
                                  .address( schoolDto.getAddress() )
                                  .contact( schoolDto.getContact() )
                                  .geoObject( geo.get() )
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