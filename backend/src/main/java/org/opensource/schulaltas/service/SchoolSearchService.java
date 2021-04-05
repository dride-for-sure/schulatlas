package org.opensource.schulaltas.service;

import org.opensource.schulaltas.model.school.AvailableType;
import org.opensource.schulaltas.model.school.School;
import org.opensource.schulaltas.repository.AvailableTypeDb;
import org.opensource.schulaltas.repository.SchoolDb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SchoolSearchService {

 private final SchoolDb schoolDb;
 private final AvailableTypeDb availableTypeDb;

 public SchoolSearchService (SchoolDb schooldb, AvailableTypeDb availableTypeDb) {
  this.schoolDb = schooldb;
  this.availableTypeDb = availableTypeDb;
 }

 public Page<School> searchForSchools (String search, int page, int size, String sort, String direction) {
  if ( direction.equals( "desc" ) ) {
   return this.schoolDb.findAllByTypeContainingIgnoreCaseOrNameContainingIgnoreCaseOrAddress_CityContainingIgnoreCaseOrAddress_StreetContainingIgnoreCaseOrAddress_PostcodeContainingIgnoreCaseOrNumberContainingIgnoreCase(
           search, search, search, search, search, search, PageRequest.of( page, size, Sort.by( sort ).descending() ) );
  } else {
   return this.schoolDb.findAllByTypeContainingIgnoreCaseOrNameContainingIgnoreCaseOrAddress_CityContainingIgnoreCaseOrAddress_StreetContainingIgnoreCaseOrAddress_PostcodeContainingIgnoreCaseOrNumberContainingIgnoreCase(
           search, search, search, search, search, search, PageRequest.of( page, size, Sort.by( sort ).ascending() ) );
  }
 }

 public Page<AvailableType> searchForTypes (String type, int page, int size, String sort, String direction) {
  if ( direction.equals( "desc" ) ) {
   return this.availableTypeDb.findAllByNameContainingIgnoreCase( type,
           PageRequest.of( page, size, Sort.by( sort ).descending() ) );
  } else {
   return this.availableTypeDb.findAllByNameContainingIgnoreCase( type,
           PageRequest.of( page, size, Sort.by( sort ).ascending() ) );
  }
 }
}
