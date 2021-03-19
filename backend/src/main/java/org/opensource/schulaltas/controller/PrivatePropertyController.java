package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.school.AvailableProperty;
import org.opensource.schulaltas.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping ("/auth/property")
public class PrivatePropertyController {

 private final PropertyService propertyService;

 public PrivatePropertyController (PropertyService propertyService) {
  this.propertyService = propertyService;
 }

 @GetMapping
 public List<AvailableProperty> listProperties () {
  return propertyService.listProperties();
 }

 @PostMapping
 public AvailableProperty addProperty (@RequestBody AvailableProperty availableProperty) {
  return propertyService.addProperty( availableProperty );
 }

 @PutMapping ("/{name}")
 public AvailableProperty updateProperty (@RequestBody AvailableProperty availableProperty) {
  return propertyService.updateProperty( availableProperty )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Could not update property: " + availableProperty.getName() ) );
 }

 @DeleteMapping ("/{name}")
 public void deleteProperty (@PathVariable String name) {
  propertyService.deleteProperty( name );
 }
}
