package org.opensource.schulaltas.service;

import org.opensource.schulaltas.model.school.Property;
import org.opensource.schulaltas.repository.PropertyDb;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

 private final PropertyDb propertyDb;

 public PropertyService (PropertyDb propertyDb) {
  this.propertyDb = propertyDb;
 }

 public List<Property> listProperties () {
  return propertyDb.findAll();
 }

 public Optional<Property> getProperty (String name) {
  return propertyDb.findById( name );
 }

 public Optional<Property> addProperty (Property property) {
  if ( !propertyDb.existsById( property.getName() ) ) {
   return Optional.of( propertyDb.save( property ) );
  }
  return Optional.empty();
 }

 public Optional<Property> updateProperty (Property property) {
  if ( propertyDb.existsById( property.getName() ) ) {
   return Optional.of( propertyDb.save( property ) );
  }
  return Optional.empty();
 }

 public void deleteProperty (String name) {
  propertyDb.deleteById( name );
 }

}
