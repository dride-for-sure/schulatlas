package org.opensource.schulaltas.service;

import org.opensource.schulaltas.controller.model.TypeDto;
import org.opensource.schulaltas.model.school.AvailableType;
import org.opensource.schulaltas.repository.AvailableTypeDb;
import org.opensource.schulaltas.repository.SchoolDb;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeService {

 private final AvailableTypeDb availableTypeDb;
 private final SchoolDb schoolDb;

 public TypeService (AvailableTypeDb availableTypeDb, SchoolDb schoolDb) {
  this.availableTypeDb = availableTypeDb;
  this.schoolDb = schoolDb;
 }

 public List<AvailableType> listAvailableTypes () {
  return availableTypeDb.findAll();
 }

 public List<TypeDto> listTypes () {
  List<String> types = schoolDb.findAll().stream()
                               .map( school -> school.getType() )
                               .collect( Collectors.toList() );
  List<String> uniqueTypes = new ArrayList<>( new HashSet<>( types ) );
  return uniqueTypes.stream()
                 .map( type -> TypeDto.builder().name( type ).count( Collections.frequency( types, type ) ).build() )
                 .collect( Collectors.toList() );
 }
}
