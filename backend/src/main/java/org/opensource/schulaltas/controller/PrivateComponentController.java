package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.component.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/auth/component")
public class PrivateComponentController {

 private final ComponentService componentService;

 public PrivateComponentController (ComponentService componentService) {
  this.componentService = componentService;
 }

 @GetMapping
 public List<Component> listComponents () {
  return componentService.listComponents();
 }
}
