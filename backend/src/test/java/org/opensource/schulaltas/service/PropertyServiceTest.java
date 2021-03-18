package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.school.AvailableProperty;
import org.opensource.schulaltas.model.school.Property;
import org.opensource.schulaltas.repository.AvailablePropertyDb;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class PropertyServiceTest {

 private final AvailablePropertyDb availablePropertyDb = mock( AvailablePropertyDb.class );
 private final PropertyService propertyService = new PropertyService( availablePropertyDb );

 @Test
 @DisplayName ("List properties should return all properties within the db")
 void listProperties () {
  // GIVEN
  AvailableProperty availableProperty1 = AvailableProperty.builder().name( "A" ).unit( "C" ).build();
  AvailableProperty availableProperty2 = AvailableProperty.builder().name( "E" ).unit( "G" ).build();
  when( availablePropertyDb.findAll() ).thenReturn( List.of( availableProperty1, availableProperty2 ) );

  // WHEN
  List<AvailableProperty> actual = propertyService.listProperties();

  // THEN
  assertThat( actual, containsInAnyOrder(
          availableProperty1.toBuilder().build(),
          availableProperty2.toBuilder().build() ) );
 }

 @Test
 @DisplayName ("Add property should add an non existing one to the db")
 void addProperty () {
  // GIVEN
  AvailableProperty availableProperty = AvailableProperty.builder().name( "A" ).unit( "C" ).build();
  when( availablePropertyDb.existsById( availableProperty.getName() ) ).thenReturn( false );
  when( availablePropertyDb.save( availableProperty ) ).thenReturn( availableProperty );

  // WHEN
  Optional<AvailableProperty> actual = propertyService.addProperty( availableProperty );

  // THEN
  assertThat( actual.get(), is( availableProperty.toBuilder().build() ) );
  verify( availablePropertyDb ).save( availableProperty );
 }

 @Test
 @DisplayName ("Add property should return an optional.empty, if it exists within db")
 void addExistingProperty () {
  // GIVEN
  AvailableProperty availableProperty = AvailableProperty.builder().name( "A" ).unit( "C" ).build();
  when( availablePropertyDb.existsById( availableProperty.getName() ) ).thenReturn( true );
  // WHEN
  Optional<AvailableProperty> actual = propertyService.addProperty( availableProperty );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( availablePropertyDb, never() ).save( availableProperty );
 }

 @Test
 @DisplayName ("Update property should store the property in the db")
 void updateProperty () {
  // GIVEN
  AvailableProperty availableProperty = AvailableProperty.builder().name( "A" ).unit( "C" ).build();
  when( availablePropertyDb.existsById( availableProperty.getName() ) ).thenReturn( true );
  when( availablePropertyDb.save( availableProperty ) ).thenReturn( availableProperty );

  // WHEN
  Optional<AvailableProperty> actual = propertyService.updateProperty( availableProperty );

  // THEN
  assertThat( actual.get(), is( availableProperty.toBuilder().build() ) );
  verify( availablePropertyDb ).save( availableProperty );
 }

 @Test
 @DisplayName ("Update property should return optional.empty, if property doesnt exists")
 void updateNotExistingProperty () {
  // GIVEN
  AvailableProperty availableProperty = AvailableProperty.builder().name( "A" ).unit( "C" ).build();
  when( availablePropertyDb.existsById( availableProperty.getName() ) ).thenReturn( false );

  // WHEN
  Optional<AvailableProperty> actual = propertyService.updateProperty( availableProperty );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( availablePropertyDb, never() ).save( availableProperty );
 }

 @Test
 @DisplayName ("Delete property should call deleteById on db")
 void deleteProperty () {
  // GIVEN
  // WHEN
  propertyService.deleteProperty( "A" );

  // THEN
  verify( availablePropertyDb ).deleteById( "A" );
 }

 @Test
 @DisplayName ("Should return true if availableProperties does contain all given properties")
 void areAvailableProperties () {
  // GIVEN
  Property property = Property.builder().name( "A" ).value( "B" ).unit( "C" ).build();

  AvailableProperty availableProperty1 = AvailableProperty.builder().name( "A" ).unit( "C" ).build();
  AvailableProperty availableProperty2 = AvailableProperty.builder().name( "B" ).unit( "C" ).build();
  when( availablePropertyDb.findAll() ).thenReturn( List.of( availableProperty1, availableProperty2 ) );

  // WHEN
  Boolean actual = propertyService.areAvailableProperties( List.of( property ) );

  // THEN
  assertThat( actual, is( true ) );
 }

 @Test
 @DisplayName ("Should return false if availableProperties does not contain all given properties")
 void containsNonExistingProperties () {
  // GIVEN
  Property property = Property.builder().name( "A" ).value( "B" ).unit( "XXXX" ).build();

  AvailableProperty availableProperty1 = AvailableProperty.builder().name( "A" ).unit( "C" ).build();
  AvailableProperty availableProperty2 = AvailableProperty.builder().name( "B" ).unit( "C" ).build();
  when( availablePropertyDb.findAll() ).thenReturn( List.of( availableProperty1, availableProperty2 ) );

  // WHEN
  Boolean actual = propertyService.areAvailableProperties( List.of( property ) );

  // THEN
  assertThat( actual, is( false ) );
 }
}