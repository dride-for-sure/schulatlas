package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.page.Assembly;
import org.opensource.schulaltas.service.AssemblyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/auth/v1/assembly")
public class PrivateAssemblyController {

 private final AssemblyService assemblyService;

 public PrivateAssemblyController (AssemblyService assemblyService) {
  this.assemblyService = assemblyService;
 }

 @GetMapping
 public List<Assembly> listComponents () {
  return assemblyService.listAssemblies();
 }
}
