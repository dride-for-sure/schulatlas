package org.opensource.schulaltas.service;

import org.opensource.schulaltas.model.page.Assembly;
import org.opensource.schulaltas.repository.AssemblyDb;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComponentService {

 private final AssemblyDb assemblyDb;

 public ComponentService (AssemblyDb assemblyDb) {
  this.assemblyDb = assemblyDb;
 }

 public List<Assembly> listComponents () {
  return assemblyDb.findAll();
 }
}
