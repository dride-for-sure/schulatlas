package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.property.Property;
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
 public List<Property> listProperties () {
  return propertyService.listProperties();
 }

 @GetMapping ("/{name}")
 public Property getProperty (@PathVariable String name) {
  return propertyService.getProperty( name )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Property: " + name + " is not available" ) );
 }

 @PostMapping
 public Property addProperty (@RequestBody Property property) {
  return propertyService.addProperty( property );
 }

 @PutMapping ("/{name}")
 public Property updateProperty (@RequestBody Property property) {
  return propertyService.updateProperty( property )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Could not update property: " + property.getName() ) );
 }

 @DeleteMapping ("/{name}")
 public void deleteProperty (@PathVariable String name) {
  propertyService.deleteProperty( name );
 }
}
