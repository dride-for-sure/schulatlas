package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.controller.model.SchoolDto;
import org.opensource.schulaltas.model.school.*;
import org.opensource.schulaltas.repository.SchoolDb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class SchoolServiceTest {

 private final SchoolDb schoolDb = mock( SchoolDb.class );
 private final GeoService geoService = mock( GeoService.class );
 private final TimeUTC timeUTC = mock( TimeUTC.class );
 private final PropertyService propertyService = mock( PropertyService.class );
 private final SchoolService schoolService = new SchoolService( schoolDb, geoService, timeUTC,
         propertyService );

 private School getSchool (String number) {
  return School.builder()
                 .number( number )
                 .name( "Goetheschule" )
                 .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                 .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                 .coordinates( Coordinates.builder().longitude( 1.1 ).latitude( 1.1 ).build() )
                 .updated( 1L )
                 .userId( "A" )
                 .markedOutdated( 0 )
                 .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                 .build();
 }

 @Test
 @DisplayName ("Lists all schools should return all schools in the asc order")
 void listAllSchoolsAscending () {
  // GIVEN
  when( schoolDb.findAll( PageRequest.of( 1, 10000, Sort.by( "number" ).ascending() ) ) )
          .thenReturn( new PageImpl<>( List.of( getSchool( "1" ), getSchool( "2" ) ) ) );

  // WHEN
  Page<School> actual = schoolService.listSchools( 1, 10000, "number", "asc" );

  // THEN
  assertThat( actual.getContent(), is( List.of( getSchool( "1" ), getSchool( "2" ) ) ) );
 }

 @Test
 @DisplayName ("Lists schools should return page with subset of schools in desc order")
 void listSchoolsPaginatedDescending () {
  // GIVEN
  when( schoolDb.findAll( PageRequest.of( 1, 1, Sort.by( "number" ).descending() ) ) )
          .thenReturn( new PageImpl<>( List.of( getSchool( "2" ) ) ) );

  // WHEN
  Page<School> actual = schoolService.listSchools( 1, 1, "number", "desc" );

  // THEN
  assertThat( actual.getContent(), is( List.of( getSchool( "2" ) ) ) );
 }

 @Test
 @DisplayName ("List all schools return a list with schools of this specific type in asc order")
 void listAllSchoolsByTypeAsc () {
  // GIVEN
  when( schoolDb.findAllByType( "A", PageRequest.of( 1, 1000, Sort.by( "number" ).ascending() ) ) )
          .thenReturn( new PageImpl<>( List.of(
                  getSchool( "A" ).toBuilder().type( "A" ).build(),
                  getSchool( "C" ).toBuilder().type( "A" ).build() ) ) );

  // WHEN
  Page<School> actual = schoolService.listSchoolsByType( "A", 1, 1000, "number", "asc" );

  // THEN
  assertThat( actual.getContent(), is( List.of(
          getSchool( "A" ).toBuilder().type( "A" ).build(),
          getSchool( "C" ).toBuilder().type( "A" ).build() ) ) );
 }

 @Test
 @DisplayName ("List schools should return a page with a subset of schools with specific type in desc order")
 void listSchoolsPaginatedByTypeDesc () {
  // GIVEN
  when( schoolDb.findAllByType( "A", PageRequest.of( 1, 1, Sort.by( "number" ).descending() ) ) )
          .thenReturn( new PageImpl<>( List.of(
                  getSchool( "A" ).toBuilder().type( "A" ).build() ) ) );

  // WHEN
  Page<School> actual = schoolService.listSchoolsByType( "A", 1, 1, "number", "desc" );

  // THEN
  assertThat( actual.getContent(), is( List.of(
          getSchool( "A" ).toBuilder().type( "A" ).build() ) ) );
 }

 @Test
 @DisplayName ("Get school should return an existing school from db")
 void getSchoolByNumber () {
  // GIVEN
  when( schoolDb.findById( "A" ) ).thenReturn( Optional.of( getSchool( "A" ) ) );

  // WHEN
  Optional<School> actual = schoolService.getSchoolByNumber( "A" );

  // THEN
  assertThat( actual.get(), is( getSchool( "A" ) ) );
 }

 @Test
 @DisplayName ("Get school should return an Optional.empty if school doesnt exists in db")
 void getNotExistingSchoolByNumber () {
  // GIVEN
  when( schoolDb.findById( "A" ) ).thenReturn( Optional.empty() );

  // WHEN
  Optional<School> actual = schoolService.getSchoolByNumber( "A" );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
 }

 @Test
 @DisplayName ("Add a new school to the db")
 void addSchool () {
  // GIVEN
  SchoolDto schoolDto = SchoolDto.builder()
                                .number( "A" )
                                .name( "Goetheschule" )
                                .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                                .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                                .userId( "A" )
                                .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                                .build();

  when( propertyService.areAvailableProperties( schoolDto.getProperties() ) ).thenReturn( true );
  when( schoolDb.findById( schoolDto.getNumber() ) ).thenReturn( Optional.empty() );
  when( geoService.getCoordinatesFromAddress( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() ) )
          .thenReturn( Optional.of( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ) );
  when( timeUTC.now() ).thenReturn( 1L );
  when( schoolDb.save( getSchool( "A" ) ) ).thenReturn( getSchool( "A" ) );

  // WHEN
  Optional<School> actual = schoolService.addSchool( schoolDto );

  // THEN
  assertThat( actual.get(), is( getSchool( "A" ) ) );
  verify( schoolDb ).save( getSchool( "A" ) );
 }

 @Test
 @DisplayName ("An already existing school could not be added again")
 void addExistingSchool () {
  // GIVEN
  SchoolDto schoolDto = SchoolDto.builder()
                                .number( "A" )
                                .name( "Goetheschule" )
                                .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                                .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                                .userId( "A" )
                                .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                                .build();

  when( propertyService.areAvailableProperties( schoolDto.getProperties() ) ).thenReturn( true );
  when( schoolDb.findById( schoolDto.getNumber() ) ).thenReturn( Optional.of( getSchool( "A" ) ) );
  when( geoService.getCoordinatesFromAddress( schoolDto.getAddress() ) )
          .thenReturn( Optional.of( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ) );

  // WHEN
  Optional<School> actual = schoolService.addSchool( schoolDto );

  // THEN
  assertThat( actual.get(), is( getSchool( "A" ) ) );
  verify( schoolDb, never() ).save( getSchool( "A" ) );

 }

 @Test
 @DisplayName ("Add a new school with an invalid address")
 void addSchoolWithInvalidAddress () {
  // GIVEN
  SchoolDto schoolDto = SchoolDto.builder()
                                .number( "A" )
                                .name( "Goetheschule" )
                                .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                                .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                                .userId( "A" )
                                .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                                .build();

  when( propertyService.areAvailableProperties( schoolDto.getProperties() ) ).thenReturn( true );
  when( schoolDb.findById( schoolDto.getNumber() ) ).thenReturn( Optional.empty() );
  when( geoService.getCoordinatesFromAddress( schoolDto.getAddress() ) ).thenReturn( Optional.empty() );

  // WHEN
  Optional<School> actual = schoolService.addSchool( schoolDto );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( schoolDb, never() ).save( getSchool( "A" ) );
 }

 @Test
 @DisplayName ("Add a new school with an invalid property")
 void addSchoolWithInvalidProperty () {
  // GIVEN
  SchoolDto schoolDto = SchoolDto.builder()
                                .number( "1337" )
                                .name( "Goetheschule" )
                                .address( Address.builder().street( "A" ).number( "B" ).city( "C" ).build() )
                                .contact( Contact.builder().phone( "A" ).email( "B" ).url( "C" ).build() )
                                .userId( "A" )
                                .properties( List.of( Property.builder().name( "A" ).value( "B" ).unit( "C" ).build() ) )
                                .build();

  when( propertyService.areAvailableProperties( schoolDto.getProperties() ) ).thenReturn( false );
  when( schoolDb.findById( schoolDto.getNumber() ) ).thenReturn( Optional.empty() );
  when( geoService.getCoordinatesFromAddress( schoolDto.getAddress() ) )
          .thenReturn( Optional.of( Coordinates.builder().latitude( 1.1 ).longitude( 1.1 ).build() ) );

  // WHEN
  Optional<School> actual = schoolService.addSchool( schoolDto );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( schoolDb, never() ).save( getSchool( "A" ) );
 }

 @Test
 @DisplayName ("Increase outdated count should increase the markedOutdated Integer by one ")
 void markOutdatedByNumber () {
  // GIVEN
  when( schoolDb.findById( "A" ) ).thenReturn( Optional.of( getSchool( "A" ) ) );

  // WHEN
  Optional<School> actual = schoolService.markOutdatedByNumber( "A" );

  // THEN
  School expected = getSchool( "A" ).toBuilder().markedOutdated( getSchool( "A" ).getMarkedOutdated() + 1 ).build();
  assertThat( actual.get(), is( expected ) );
  verify( schoolDb ).findById( "A" );
 }

 @Test
 @DisplayName ("Increase outdated count should return an optional.empty if school is not in db")
 void updateSchool () {
  // GIVEN
  when( schoolDb.findById( "A" ) ).thenReturn( Optional.empty() );

  // WHEN
  Optional<School> actual = schoolService.markOutdatedByNumber( "A" );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
 }

 @Test
 @DisplayName ("Delete School should delete school from db")
 void deleteSchoolByNumber () {
  // WHEN
  schoolService.deleteSchoolByNumber( "A" );

  // THEN
  verify( schoolDb ).deleteById( "A" );
 }
}