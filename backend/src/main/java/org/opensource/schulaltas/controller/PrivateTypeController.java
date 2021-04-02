package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.controller.model.TypeDto;
import org.opensource.schulaltas.model.school.AvailableType;
import org.opensource.schulaltas.service.TypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/auth/v1/type")
public class PrivateTypeController {

 private final TypeService typeService;

 public PrivateTypeController (TypeService typeService) {
  this.typeService = typeService;
 }

 @GetMapping ("/used")
 public List<TypeDto> listTypes () {
  return typeService.listTypes();
 }

 @GetMapping ("available")
 public List<AvailableType> listAvailableTypes () {
  return typeService.listAvailableTypes();
 }
}
