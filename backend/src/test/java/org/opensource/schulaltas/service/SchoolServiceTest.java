package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.controller.model.SchoolDto;
import org.opensource.schulaltas.model.school.*;
import org.opensource.schulaltas.repository.SchoolDb;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class SchoolServiceTest {

 private final SchoolDb schoolDb = mock( SchoolDb.class );
 private final GeoService geoService = mock( GeoService.class );
 private final TimeUtil timeUtil = mock( TimeUtil.class );
 private final SchoolService schoolService = new SchoolService( schoolDb, geoService, timeUtil );

 @Test
 @DisplayName ("Lists schools should return all schools within the db")
 void listSchools () {
  // GIVEN
  School school1 = School.builder()
                           .number( "1337" )
                           .name( "Goetheschule" )
                           .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                           .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                           .geoObject( GeoObject.builder().coordinates( Coordinates.builder().longitude( 1.1 ).latitude( 1.1 ).build() ).build() )
                           .updated( 1L )
                           .userId( "A" )
                           .markedOutdated( 0 )
                           .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                           .build();

  School school2 = School.builder()
                           .number( "7331" )
                           .name( "Goetheschule" )
                           .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                           .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                           .geoObject( GeoObject.builder().coordinates( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ).build() )
                           .updated( 1L )
                           .userId( "A" )
                           .markedOutdated( 0 )
                           .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                           .build();
  when( schoolDb.findAll() ).thenReturn( List.of( school1, school2 ) );

  // WHEN
  List<School> actual = schoolService.listSchools();

  // THEN
  assertThat( actual, containsInAnyOrder(
          school1.toBuilder().build(),
          school2.toBuilder().build() ) );
 }

 @Test
 @DisplayName ("Get school should return an existing school from db")
 void getSchool () {
  // GIVEN
  String number = "1337";
  School school = School.builder()
                          .number( "1337" )
                          .name( "Goetheschule" )
                          .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                          .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                          .geoObject( GeoObject.builder().coordinates( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ).build() )
                          .updated( 1L )
                          .userId( "A" )
                          .markedOutdated( 0 )
                          .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                          .build();
  when( schoolDb.findById( number ) ).thenReturn(
          Optional.of( school ) );

  // WHEN
  Optional<School> actual = schoolService.getSchool( number );

  // THEN
  School expected = school.toBuilder().build();
  assertThat( actual.get(), is( expected ) );
 }

 @Test
 @DisplayName ("Get school should return an Optional.empty if school doesnt exists in db")
 void getNotExistingSchool () {
  // GIVEN
  when( schoolDb.findById( "1337" ) ).thenReturn( Optional.empty() );

  // WHEN
  Optional<School> actual = schoolService.getSchool( "1337" );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
 }

 @Test
 @DisplayName ("Add a new school to the db")
 void addSchool () {
  // GIVEN
  SchoolDto schoolDto = SchoolDto.builder()
                                .number( "1337" )
                                .name( "Goetheschule" )
                                .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                                .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                                .userId( "A" )
                                .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                                .build();

  School school = School.builder()
                          .number( "1337" )
                          .name( "Goetheschule" )
                          .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                          .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                          .geoObject( GeoObject.builder().coordinates( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ).build() )
                          .updated( 1L )
                          .userId( "A" )
                          .markedOutdated( 0 )
                          .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                          .build();

  when( schoolDb.findById( schoolDto.getNumber() ) ).thenReturn( Optional.empty() );
  when( geoService.getCoordinatesFromAddress( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() ) )
          .thenReturn( Optional.of( GeoObject.builder().coordinates( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ).build() ) );
  when( timeUtil.now() ).thenReturn( 1L );
  when( schoolDb.save( school ) ).thenReturn( school );

  // WHEN
  Optional<School> actual = schoolService.addSchool( schoolDto );

  // THEN
  School expected = school.toBuilder().build();
  assertThat( actual.get(), is( expected ) );
  verify( schoolDb ).save( school );
 }

 @Test
 @DisplayName ("An already existing school could not be added again")
 void addExistingSchool () {
  // GIVEN
  SchoolDto schoolDto = SchoolDto.builder()
                                .number( "1337" )
                                .name( "Goetheschule" )
                                .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                                .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                                .userId( "A" )
                                .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                                .build();
  School school = School.builder()
                          .number( schoolDto.getNumber() )
                          .name( schoolDto.getName() )
                          .address( schoolDto.getAddress() )
                          .contact( schoolDto.getContact() )
                          .geoObject( GeoObject.builder().coordinates( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ).build() )
                          .updated( 1L )
                          .userId( schoolDto.getUserId() )
                          .markedOutdated( 0 )
                          .properties( schoolDto.getProperties() )
                          .build();

  when( schoolDb.findById( schoolDto.getNumber() ) ).thenReturn( Optional.of( school ) );
  when( geoService.getCoordinatesFromAddress( schoolDto.getAddress() ) )
          .thenReturn( Optional.of( GeoObject.builder().coordinates( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ).build() ) );

  // WHEN
  Optional<School> actual = schoolService.addSchool( schoolDto );

  // THEN
  assertThat( actual.get(), is( school.toBuilder().build() ) );
  verify( schoolDb, never() ).save( school.toBuilder().build() );

 }

 @Test
 @DisplayName ("Add a new school with an invalid address")
 void addSchoolWithInvalidAddress () {
  // GIVEN
  SchoolDto schoolDto = SchoolDto.builder()
                                .number( "1337" )
                                .name( "Goetheschule" )
                                .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                                .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                                .userId( "A" )
                                .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                                .build();

  when( schoolDb.findById( schoolDto.getNumber() ) ).thenReturn( Optional.empty() );
  when( geoService.getCoordinatesFromAddress( schoolDto.getAddress() ) ).thenReturn( Optional.empty() );

  // WHEN
  Optional<School> actual = schoolService.addSchool( schoolDto );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
 }

 @Test
 @DisplayName ("Increase outdated count should increase the markedOutdated Integer by one ")
 void increaseOutdatedCount () {
  // GIVEN
  School school = School.builder()
                          .number( "1337" )
                          .name( "Goetheschule" )
                          .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                          .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                          .geoObject( GeoObject.builder().coordinates( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ).build() )
                          .updated( 1L )
                          .userId( "A" )
                          .markedOutdated( 0 )
                          .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                          .build();

  when( schoolDb.findById( school.getNumber() ) ).thenReturn( Optional.of( school ) );

  // WHEN
  Optional<School> actual = schoolService.increaseOutdatedCount( school.getNumber() );

  // THEN
  School expected = school.toBuilder().markedOutdated( school.getMarkedOutdated() + 1 ).build();
  assertThat( actual.get(), is( expected ) );
  verify( schoolDb ).findById( school.getNumber() );
 }

 @Test
 @DisplayName ("Increase outdated count should return an optional.empty if school is not in db")
 void updateSchool () {
  // GIVEN
  when( schoolDb.findById( "1337" ) ).thenReturn( Optional.empty() );

  // WHEN
  Optional<School> actual = schoolService.increaseOutdatedCount( "1337" );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
 }

 @Test
 @DisplayName ("Delete School should delete school from db")
 void deleteSchool () {
  // WHEN
  schoolService.deleteSchool( "1337" );

  // THEN
  verify( schoolDb ).deleteById( "1337" );
 }
}