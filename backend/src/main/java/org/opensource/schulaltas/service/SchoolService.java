package org.opensource.schulaltas.service;


import org.opensource.schulaltas.controller.model.SchoolDto;
import org.opensource.schulaltas.controller.model.SchoolImportDto;
import org.opensource.schulaltas.model.school.Address;
import org.opensource.schulaltas.model.school.Contact;
import org.opensource.schulaltas.model.school.Coordinates;
import org.opensource.schulaltas.model.school.School;
import org.opensource.schulaltas.repository.SchoolDb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolService {

 private final SchoolDb schoolDb;
 private final GeoService geoService;
 private final TimeUTC timeUTC;
 private final PropertyService propertyService;

 public SchoolService (SchoolDb schoolDb, GeoService geoService, TimeUTC timeUTC,
                       PropertyService propertyService) {
  this.schoolDb = schoolDb;
  this.geoService = geoService;
  this.timeUTC = timeUTC;
  this.propertyService = propertyService;
 }

 public Page<School> listSchools (int page, int size, String sort, String direction) {
  if ( direction.equals( "desc" ) ) {
   return schoolDb.findAll( PageRequest.of( page, size, Sort.by( sort ).descending() ) );
  }
  return schoolDb.findAll( PageRequest.of( page, size, Sort.by( sort ).ascending() ) );
 }

 public Page<School> listSchoolsByType (String type, int page, int size, String sort, String direction) {
  if ( direction.equals( "desc" ) ) {
   return schoolDb.findAllByType( type, PageRequest.of( page, size, Sort.by( sort ).descending() ) );
  }
  return schoolDb.findAllByType( type, PageRequest.of( page, size, Sort.by( sort ).ascending() ) );
 }

 public Optional<School> getSchoolByNumber (String number) {
  return schoolDb.findById( number );
 }

 public Optional<School> addSchool (SchoolDto schoolDto) {
  if ( schoolDto.getNumber().isEmpty() ) {
   return Optional.empty();
  }
  Optional<Coordinates> coordinates = geoService.getCoordinatesFromAddress( schoolDto.getAddress() );
  Optional<School> school = schoolDb.findById( schoolDto.getNumber() );
  Boolean areAvailableProperties = propertyService.areAvailableProperties( schoolDto.getProperties() );
  if ( school.isEmpty() ) {
   if ( coordinates.isPresent() && areAvailableProperties ) {
    School newSchool = School.builder()
                               .number( schoolDto.getNumber() )
                               .name( schoolDto.getName() )
                               .address( schoolDto.getAddress() )
                               .contact( schoolDto.getContact() )
                               .type( schoolDto.getType() )
                               .image( schoolDto.getImage() )
                               .coordinates( coordinates.get() )
                               .updated( timeUTC.now() )
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

 public Optional<School> markOutdatedByNumber (String number) {
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
  Optional<Coordinates> coordinates = geoService.getCoordinatesFromAddress( schoolDto.getAddress() );
  Optional<School> schoolToUpdate = schoolDb.findById( schoolDto.getNumber() );
  Boolean areAvailableProperties = propertyService.areAvailableProperties( schoolDto.getProperties() );
  if ( schoolToUpdate.isPresent() && coordinates.isPresent() && areAvailableProperties ) {
   School updatedSchool = schoolToUpdate.get()
                                  .toBuilder()
                                  .name( schoolDto.getName() )
                                  .address( schoolDto.getAddress() )
                                  .contact( schoolDto.getContact() )
                                  .type( schoolDto.getType() )
                                  .image( schoolDto.getImage() )
                                  .coordinates( coordinates.get() )
                                  .userId( schoolDto.getUserId() )
                                  .updated( timeUTC.now() )
                                  .properties( schoolDto.getProperties() )
                                  .build();
   schoolDb.save( updatedSchool );
   return Optional.of( updatedSchool );
  }
  return Optional.empty();
 }

 public void deleteSchoolByNumber (String number) {
  schoolDb.deleteById( number );
 }

 public void importSchools (List<SchoolImportDto> schoolImportDtos) {
  schoolImportDtos.forEach( schoolImportDto -> {
   if ( schoolImportDto.getAddress() != null ) {
    String[] addressArray = schoolImportDto.getAddress().split( "," );
    String streetName = addressArray[ 0 ].replaceAll( "[0-9]{1,3}(\\w{0,2})(-[0-9]{1,3}(\\w{0,2}))?", "" ).trim();
    String streetNumber = addressArray[ 0 ].substring( streetName.length() ).trim();
    String postCode = addressArray[ 1 ].replaceAll( "\\D+", "" ).trim();
    String city = addressArray[ 1 ].substring( postCode.length() + 1 ).trim();
    School schoolToSave = School.builder()
                                  .number( schoolImportDto.getOfficial_id() )
                                  .name( schoolImportDto.getName() )
                                  .type( schoolImportDto.getSchool_type() )
                                  .image( "https://schulatlas.s3.amazonaws.com/796f73de-aa37-4b97-905c-38362af91273.png" )
                                  .address( Address.builder()
                                                    .street( streetName )
                                                    .number( streetNumber )
                                                    .city( city )
                                                    .postcode( postCode )
                                                    .build() )
                                  .contact( Contact.builder().email( "undefined" ).phone(
                                          "undefined" ).url( "undefined" ).build() )
                                  .coordinates( Coordinates.builder()
                                                        .latitude( Double.parseDouble( schoolImportDto.getLat() ) )
                                                        .longitude( Double.parseDouble( schoolImportDto.getLon() ) )
                                                        .build() )
                                  .updated( timeUTC.now() )
                                  .properties( List.of() )
                                  .build();
    schoolDb.save( schoolToSave );
   }
  } );
 }
}