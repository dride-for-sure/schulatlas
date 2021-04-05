package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.school.AvailableType;
import org.opensource.schulaltas.model.school.School;
import org.opensource.schulaltas.repository.AvailableTypeDb;
import org.opensource.schulaltas.repository.SchoolDb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SchoolSearchServiceTest {

 private final SchoolDb schoolDb = mock( SchoolDb.class );
 private final AvailableTypeDb availableTypeDb = mock( AvailableTypeDb.class );
 private final SchoolSearchService schoolSearchService = new SchoolSearchService( this.schoolDb,
         this.availableTypeDb );

 @Test
 @DisplayName ("Search for a given string should return the correct Page (dir: asc) with results")
 void searchForSchoolsWithAscendingOrder () {
  // GIVEN
  School schoolA = School.builder().name( "A" ).build();
  School schoolB = School.builder().name( "B" ).build();
  PageRequest pageRequest = PageRequest.of( 1, 2, Sort.by( "name" ).ascending() );
  when( this.schoolDb.findAllByTypeContainingIgnoreCaseOrNameContainingIgnoreCaseOrAddress_CityContainingIgnoreCaseOrAddress_StreetContainingIgnoreCaseOrAddress_PostcodeContainingIgnoreCaseOrNumberContainingIgnoreCase( "A", "A", "A", "A", "A", "A", pageRequest ) )
          .thenReturn( new PageImpl<>( List.of( schoolA, schoolB ) ) );

  // WHEN
  Page<School> actual = this.schoolSearchService.searchForSchools( "A", 1, 2, "name", "asc" );

  // THEN
  assertThat( actual.getContent(), containsInAnyOrder( School.builder().name( "A" ).build(),
          School.builder().name( "B" ).build() ) );
 }

 @Test
 @DisplayName ("Search for a given string should return the correct Page (dir: desc) with results")
 void searchForSchoolsWithDescendingOrder () {
  // GIVEN
  School schoolA = School.builder().name( "A" ).build();
  School schoolB = School.builder().name( "B" ).build();
  PageRequest pageRequest = PageRequest.of( 1, 2, Sort.by( "name" ).descending() );
  when( this.schoolDb.findAllByTypeContainingIgnoreCaseOrNameContainingIgnoreCaseOrAddress_CityContainingIgnoreCaseOrAddress_StreetContainingIgnoreCaseOrAddress_PostcodeContainingIgnoreCaseOrNumberContainingIgnoreCase( "A", "A", "A", "A", "A", "A", pageRequest ) )
          .thenReturn( new PageImpl<>( List.of( schoolB, schoolA ) ) );

  // WHEN
  Page<School> actual = this.schoolSearchService.searchForSchools( "A", 1, 2, "name", "desc" );

  // THEN
  assertThat( actual.getContent(), containsInAnyOrder( School.builder().name( "B" ).build(),
          School.builder().name( "A" ).build() ) );
 }

 @Test
 @DisplayName ("Search for a given string that is not found should return the correct Page without results")
 void searchForSchoolsWithoutResults () {
  // GIVEN
  PageRequest pageRequest = PageRequest.of( 1, 2, Sort.by( "name" ).descending() );
  when( this.schoolDb.findAllByTypeContainingIgnoreCaseOrNameContainingIgnoreCaseOrAddress_CityContainingIgnoreCaseOrAddress_StreetContainingIgnoreCaseOrAddress_PostcodeContainingIgnoreCaseOrNumberContainingIgnoreCase( "A", "A", "A", "A", "A", "A", pageRequest ) )
          .thenReturn( new PageImpl<>( List.of() ) );

  // WHEN
  Page<School> actual = this.schoolSearchService.searchForSchools( "A", 1, 2, "name", "desc" );

  // THEN
  assertThat( actual.getContent().isEmpty(), is( true ) );
 }

 @Test
 @DisplayName ("Search for a given type should return the correct Page (dir: asc) with results")
 void searchForTypesWithAscendingOrder () {
  // GIVEN
  AvailableType availableTypeA = AvailableType.builder().name( "A" ).build();
  AvailableType availableTypeB = AvailableType.builder().name( "B" ).build();
  PageRequest pageRequest = PageRequest.of( 1, 2, Sort.by( "name" ).ascending() );
  when( this.availableTypeDb.findAllByNameContainingIgnoreCase( "A", pageRequest ) )
          .thenReturn( new PageImpl<>( List.of( availableTypeA, availableTypeB ) ) );

  // WHEN
  Page<AvailableType> actual = this.schoolSearchService.searchForTypes( "A", 1, 2, "name", "asc" );

  // THEN
  assertThat( actual.getContent(), containsInAnyOrder( AvailableType.builder().name( "A" ).build(),
          AvailableType.builder().name( "B" ).build() ) );
 }

 @Test
 @DisplayName ("Search for a given type should return the correct Page (dir: desc) with results")
 void searchForTypesWithDescendingOrder () {
  // GIVEN
  AvailableType availableTypeA = AvailableType.builder().name( "A" ).build();
  AvailableType availableTypeB = AvailableType.builder().name( "B" ).build();
  PageRequest pageRequest = PageRequest.of( 1, 2, Sort.by( "name" ).descending() );
  when( this.availableTypeDb.findAllByNameContainingIgnoreCase( "A", pageRequest ) )
          .thenReturn( new PageImpl<>( List.of( availableTypeB, availableTypeA ) ) );

  // WHEN
  Page<AvailableType> actual = this.schoolSearchService.searchForTypes( "A", 1, 2, "name", "desc" );

  // THEN
  assertThat( actual.getContent(), containsInAnyOrder( AvailableType.builder().name( "B" ).build(),
          AvailableType.builder().name( "A" ).build() ) );
 }


 @Test
 @DisplayName ("Search for a given type that is not found should return the correct Page without results")
 void searchForTypesWithoutResults () {
  // GIVEN
  PageRequest pageRequest = PageRequest.of( 1, 2, Sort.by( "name" ).ascending() );
  when( this.availableTypeDb.findAllByNameContainingIgnoreCase( "A", pageRequest ) )
          .thenReturn( new PageImpl<>( List.of() ) );

  // WHEN
  Page<AvailableType> actual = this.schoolSearchService.searchForTypes( "A", 1, 2, "name", "asc" );

  // THEN
  assertThat( actual.getContent().isEmpty(), is( true ) );
 }
}