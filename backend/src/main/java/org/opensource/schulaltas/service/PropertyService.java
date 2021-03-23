package org.opensource.schulaltas.service;

import org.opensource.schulaltas.model.school.AvailableProperty;
import org.opensource.schulaltas.model.school.Property;
import org.opensource.schulaltas.repository.AvailablePropertyDb;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PropertyService {

 private final AvailablePropertyDb availablePropertyDb;

 public PropertyService (AvailablePropertyDb availablePropertyDb) {
  this.availablePropertyDb = availablePropertyDb;
 }

 public List<AvailableProperty> listProperties () {
  return availablePropertyDb.findAll();
 }

 public AvailableProperty addProperty (AvailableProperty availableProperty) {
  Optional<AvailableProperty> existing = availablePropertyDb.findById( availableProperty.getName() );
  if ( existing.isEmpty() ) {
   return availablePropertyDb.save( availableProperty );
  }
  return existing.get();
 }

 public Optional<AvailableProperty> updateProperty (AvailableProperty availableProperty) {
  if ( availablePropertyDb.existsById( availableProperty.getName() ) ) {
   return Optional.of( availablePropertyDb.save( availableProperty ) );
  }
  return Optional.empty();
 }

 public void deletePropertyByName (String name) {
  availablePropertyDb.deleteById( name );
 }

 public Boolean areAvailableProperties (List<Property> properties) {
  List<Property> availableProperties =
          availablePropertyDb.findAll()
                  .stream()
                  .map( availableProperty -> Property.builder()
                                                     .name( availableProperty.getName() )
                                                     .unit( availableProperty.getUnit() )
                                                     .build() )
                  .collect( Collectors.toList() );
  List<Property> givenProperties = properties.stream()
                                           .map( property -> Property.builder()
                                                                     .name( property.getName() )
                                                                     .unit( property.getUnit() )
                                                                     .build() )
                                           .collect( Collectors.toList() );
  return availableProperties.containsAll( givenProperties );
 }
}
