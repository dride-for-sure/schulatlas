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

 private AvailableProperty getProperty (String name) {
  return AvailableProperty.builder().name( name ).unit( "B" ).build();
 }

 @Test
 @DisplayName ("List properties should return all properties within the db")
 void listProperties () {
  // GIVEN
  when( availablePropertyDb.findAll() ).thenReturn( List.of( getProperty( "A" ), getProperty( "B" ) ) );

  // WHEN
  List<AvailableProperty> actual = propertyService.listProperties();

  // THEN
  assertThat( actual, containsInAnyOrder(
          getProperty( "A" ),
          getProperty( "B" ) ) );
 }

 @Test
 @DisplayName ("Add property should add an non existing one to the db")
 void addProperty () {
  // GIVEN
  when( availablePropertyDb.findById( "A" ) ).thenReturn( Optional.empty() );
  when( availablePropertyDb.save( getProperty( "A" ) ) ).thenReturn( getProperty( "A" ) );

  // WHEN
  AvailableProperty actual = propertyService.addProperty( getProperty( "A" ) );

  // THEN
  assertThat( actual, is( getProperty( "A" ) ) );
  verify( availablePropertyDb ).save( getProperty( "A" ) );
 }

 @Test
 @DisplayName ("Add property should return the existing property")
 void addExistingProperty () {

  // GIVEN
  when( availablePropertyDb.findById( "A" ) ).thenReturn( Optional.of( getProperty( "A" ) ) );

  // WHEN
  AvailableProperty actual = propertyService.addProperty( getProperty( "A" ) );

  // THEN
  assertThat( actual, is( getProperty( "A" ) ) );
  verify( availablePropertyDb, never() ).save( getProperty( "A" ) );
 }

 @Test
 @DisplayName ("Update property should store the property in the db")
 void updateProperty () {
  // GIVEN
  when( availablePropertyDb.existsById( "A" ) ).thenReturn( true );
  when( availablePropertyDb.save( getProperty( "A" ) ) ).thenReturn( getProperty( "A" ) );

  // WHEN
  Optional<AvailableProperty> actual = propertyService.updateProperty( getProperty( "A" ) );

  // THEN
  assertThat( actual.get(), is( getProperty( "A" ) ) );
  verify( availablePropertyDb ).save( getProperty( "A" ) );
 }

 @Test
 @DisplayName ("Update property should return optional.empty, if property doesnt exists")
 void updateNotExistingProperty () {
  // GIVEN
  when( availablePropertyDb.existsById( "A" ) ).thenReturn( false );

  // WHEN
  Optional<AvailableProperty> actual = propertyService.updateProperty( getProperty( "A" ) );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( availablePropertyDb, never() ).save( getProperty( "A" ) );
 }

 @Test
 @DisplayName ("Delete property should call deleteById on db")
 void deletePropertyByName () {
  // GIVEN
  // WHEN
  propertyService.deletePropertyByName( "A" );

  // THEN
  verify( availablePropertyDb ).deleteById( "A" );
 }

 @Test
 @DisplayName ("Should return true if availableProperties does contain all given properties")
 void areAvailableProperties () {
  // GIVEN
  Property property = Property.builder().name( "A" ).unit( "B" ).value( "C" ).build();
  when( availablePropertyDb.findAll() ).thenReturn( List.of( getProperty( "A" ), getProperty( "B" ) ) );

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
  when( availablePropertyDb.findAll() ).thenReturn( List.of( getProperty( "A" ), getProperty( "B" ) ) );

  // WHEN
  Boolean actual = propertyService.areAvailableProperties( List.of( property ) );

  // THEN
  assertThat( actual, is( false ) );
 }
}