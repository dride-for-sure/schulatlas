package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.school.Property;
import org.opensource.schulaltas.repository.PropertyDb;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class PropertyServiceTest {

 private final PropertyDb propertyDb = mock( PropertyDb.class );
 private final PropertyService propertyService = new PropertyService( propertyDb );

 @Test
 @DisplayName ("List properties should return all properties within the db")
 void listProperties () {
  // GIVEN
  Property property1 = Property.builder().name( "A" ).value( "B" ).unit( "C" ).build();
  Property property2 = Property.builder().name( "E" ).value( "F" ).unit( "G" ).build();
  when( propertyDb.findAll() ).thenReturn( List.of( property1, property2 ) );

  // WHEN
  List<Property> actual = propertyService.listProperties();

  // THEN
  assertThat( actual, containsInAnyOrder(
          property1.toBuilder().build(),
          property2.toBuilder().build() ) );
 }

 @Test
 @DisplayName ("Get property should return an property")
 void getProperty () {
  // GIVEN
  Property property = Property.builder().name( "A" ).value( "B" ).unit( "C" ).build();
  when( propertyDb.findById( property.getName() ) ).thenReturn( Optional.of( property ) );

  // WHEN
  Optional<Property> actual = propertyService.getProperty( "A" );

  // THEN
  assertThat( actual.get(), is( property.toBuilder().build() ) );
 }

 @Test
 @DisplayName ("Get property should return an optional.empty, if it does not exists within db")
 void getNotExistingProperty () {
  // GIVEN
  when( propertyDb.findById( "A" ) ).thenReturn( Optional.empty() );

  // WHEN
  Optional<Property> actual = propertyService.getProperty( "A" );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
 }

 @Test
 @DisplayName ("Add property should add an non existing one to the db")
 void addProperty () {
  // GIVEN
  Property property = Property.builder().name( "A" ).value( "B" ).unit( "C" ).build();
  when( propertyDb.existsById( property.getName() ) ).thenReturn( false );
  when( propertyDb.save( property ) ).thenReturn( property );

  // WHEN
  Optional<Property> actual = propertyService.addProperty( property );

  // THEN
  assertThat( actual.get(), is( property.toBuilder().build() ) );
  verify( propertyDb ).save( property );
 }

 @Test
 @DisplayName ("Add property should return an optional.empty, if it exists within db")
 void addExistingProperty () {
  // GIVEN
  Property property = Property.builder().name( "A" ).value( "B" ).unit( "C" ).build();
  when( propertyDb.existsById( property.getName() ) ).thenReturn( true );
  // WHEN
  Optional<Property> actual = propertyService.addProperty( property );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( propertyDb, never() ).save( property );
 }

 @Test
 @DisplayName ("Update property should store the property in the db")
 void updateProperty () {
  // GIVEN
  Property property = Property.builder().name( "A" ).value( "B" ).unit( "C" ).build();
  when( propertyDb.existsById( property.getName() ) ).thenReturn( true );
  when( propertyDb.save( property ) ).thenReturn( property );

  // WHEN
  Optional<Property> actual = propertyService.updateProperty( property );

  // THEN
  assertThat( actual.get(), is( property.toBuilder().build() ) );
  verify( propertyDb ).save( property );
 }

 @Test
 @DisplayName ("Update property should return optional.empty, if property doesnt exists")
 void updateNotExistingProperty () {
  // GIVEN
  Property property = Property.builder().name( "A" ).value( "B" ).unit( "C" ).build();
  when( propertyDb.existsById( property.getName() ) ).thenReturn( false );

  // WHEN
  Optional<Property> actual = propertyService.updateProperty( property );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( propertyDb, never() ).save( property );
 }

 @Test
 void deleteProperty () {
  // GIVEN
  // WHEN
  propertyService.deleteProperty( "A" );

  // THEN
  verify( propertyDb ).deleteById( "A" );
 }
}