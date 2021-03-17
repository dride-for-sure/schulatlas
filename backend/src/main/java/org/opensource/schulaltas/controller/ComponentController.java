package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.component.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/api")
public class ComponentController {

 private final ComponentService componentService;

 public ComponentController (ComponentService componentService) {
  this.componentService = componentService;
 }

 public List<Component> listComponents () {
  return componentService.listComponents();
 }
}
