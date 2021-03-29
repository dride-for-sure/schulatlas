package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.website.Assembly;
import org.opensource.schulaltas.repository.AssemblyDb;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AssemblyServiceTest {

 private final AssemblyDb assemblyDb = mock( AssemblyDb.class );
 private final AssemblyService assemblyService = new AssemblyService( assemblyDb );


 private Assembly getAssembly (String name) {
  return Assembly.builder().type( name ).components( List.of() ).build();
 }

 @Test
 @DisplayName ("List components should return all components form the db")
 void listComponents () {
  // GIVEN
  when( assemblyDb.findAll() ).thenReturn( List.of( getAssembly( "A" ), getAssembly( "B" ) ) );

  // WHEN
  List<Assembly> actual = assemblyService.listAssemblies();

  // THEN
  assertThat( actual, containsInAnyOrder( getAssembly( "A" ), getAssembly( "B" ) ) );
 }

 @Test
 @DisplayName ("Return true if assemblies are within the db")
 void hasValidAssemblies () {
  // GIVEN
  when( assemblyDb.findAll() ).thenReturn( List.of( getAssembly( "A" ), getAssembly( "B" ) ) );
  // WHEN
  List<Assembly> assemblies = List.of( getAssembly( "A" ), getAssembly( "B" ) );
  Boolean actual = assemblyService.hasAvailableAssemblies( assemblies );

  // THEN
  assertThat( actual, is( true ) );
 }

 @Test
 @DisplayName ("Return false if assemblies are not available within the db")
 void hasInvalidAssemblies () {
  // GIVEN
  when( assemblyDb.findAll() ).thenReturn( List.of( getAssembly( "A" ), getAssembly( "B" ) ) );
  // WHEN
  List<Assembly> assemblies = List.of( getAssembly( "A" ), getAssembly( "INVALID" ) );
  Boolean actual = assemblyService.hasAvailableAssemblies( assemblies );

  // THEN
  assertThat( actual, is( false ) );
 }
}