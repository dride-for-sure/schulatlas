package org.opensource.schulaltas.service;

import org.opensource.schulaltas.model.page.Component;
import org.opensource.schulaltas.repository.ComponentDb;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComponentService {

 private final ComponentDb componentDb;

 public ComponentService (ComponentDb componentDb) {
  this.componentDb = componentDb;
 }

 public List<Component> listComponents () {
  return componentDb.findAll();
 }
}
