package org.opensource.schulaltas.service;

import org.opensource.schulaltas.model.page.Assembly;
import org.opensource.schulaltas.repository.AssemblyDb;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssemblyService {

 private final AssemblyDb assemblyDb;

 public AssemblyService (AssemblyDb assemblyDb) {
  this.assemblyDb = assemblyDb;
 }

 public List<Assembly> listAssemblies () {
  return assemblyDb.findAll();
 }

 public boolean hasAvailableAssemblies (List<Assembly> assemblies) {
  List<String> availableAssemblyTypes = assemblyDb.findAll().stream()
                                                .map( Assembly::getType )
                                                .collect( Collectors.toList() );
  List<String> requestedAssemblyTypes = assemblies.stream()
                                                .map( Assembly::getType )
                                                .collect( Collectors.toList() );
  return availableAssemblyTypes.containsAll( requestedAssemblyTypes );
 }
}
