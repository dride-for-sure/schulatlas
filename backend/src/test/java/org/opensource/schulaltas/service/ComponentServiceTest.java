package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.page.Component;
import org.opensource.schulaltas.repository.ComponentDb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ComponentServiceTest {

 private final ComponentDb componentDb = mock( ComponentDb.class );
 private final ComponentService componentService = new ComponentService( componentDb );

 @Test
 @DisplayName ("List components should return all components form the db")
 void listComponents () {
  // GIVEN
  Map<String, List<Object>> components1 = new HashMap<>();
  components1.put( "A", List.of() );
  Map<String, List<Object>> components2 = new HashMap<>();
  components2.put( "B", List.of() );
  Component component1 = Component.builder().components( components1 ).build();
  Component component2 = Component.builder().components( components2 ).build();
  when( componentDb.findAll() ).thenReturn( List.of( component1, component2 ) );

  // WHEN
  List<Component> actual = componentService.listComponents();

  // THEN
  assertThat( actual, containsInAnyOrder(
          component1.toBuilder().build(),
          component2.toBuilder().build() ) );
 }
}