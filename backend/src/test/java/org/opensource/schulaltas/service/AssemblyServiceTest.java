package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.page.Assembly;
import org.opensource.schulaltas.repository.AssemblyDb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AssemblyServiceTest {

 private final AssemblyDb assemblyDb = mock( AssemblyDb.class );
 private final ComponentService componentService = new ComponentService( assemblyDb );

 @Test
 @DisplayName ("List components should return all components form the db")
 void listComponents () {
  // GIVEN
  Map<String, List<Object>> components1 = new HashMap<>();
  components1.put( "A", List.of() );
  Map<String, List<Object>> components2 = new HashMap<>();
  components2.put( "B", List.of() );
  Assembly assembly1 = Assembly.builder().components( List.of() ).build();
  Assembly assembly2 = Assembly.builder().components( List.of() ).build();
  when( assemblyDb.findAll() ).thenReturn( List.of( assembly1, assembly2 ) );

  // WHEN
  List<Assembly> actual = componentService.listComponents();

  // THEN
  assertThat( actual, containsInAnyOrder(
          assembly1.toBuilder().build(),
          assembly2.toBuilder().build() ) );
 }
}