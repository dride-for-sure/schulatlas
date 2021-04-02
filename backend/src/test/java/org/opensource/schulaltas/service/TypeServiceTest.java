package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.controller.model.TypeDto;
import org.opensource.schulaltas.model.school.*;
import org.opensource.schulaltas.repository.AvailableTypeDb;
import org.opensource.schulaltas.repository.SchoolDb;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TypeServiceTest {

 private final SchoolDb schoolDb = mock( SchoolDb.class );
 private final AvailableTypeDb availableTypeDb = mock( AvailableTypeDb.class );
 private final TypeService typeService = new TypeService( availableTypeDb, schoolDb );

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

 private AvailableType getAvailableType (String name) {
  return AvailableType.builder()
                 .name( name )
                 .build();
 }

 @Test
 @DisplayName ("List types should filter all schools for unique types")
 void listTypes () {
  // GIVEN
  when( schoolDb.findAll() ).thenReturn( List.of(
          getSchool( "A" ).toBuilder().type( "A" ).build(),
          getSchool( "B" ).toBuilder().type( "B" ).build(),
          getSchool( "C" ).toBuilder().type( "A" ).build() ) );

  // WHEN
  List<TypeDto> actual = typeService.listTypes();

  // THEN
  assertThat( actual, is( List.of(
          TypeDto.builder().name( "A" ).count( 2 ).build(),
          TypeDto.builder().name( "B" ).count( 1 ).build()
  ) ) );
 }

 @Test
 @DisplayName ("List available types should return all available types")
 void listAvailableTypes () {
  // GIVEN
  when( availableTypeDb.findAll() ).thenReturn( List.of(
          getAvailableType( "A" ),
          getAvailableType( "B" ) ) );

  // WHEN
  List<AvailableType> actual = typeService.listAvailableTypes();

  // THEN
  assertThat( actual, is( List.of(
          getAvailableType( "A" ),
          getAvailableType( "B" )
  ) ) );
 }
}
