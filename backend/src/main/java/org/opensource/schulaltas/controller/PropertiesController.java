package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.property.Property;
import org.opensource.schulaltas.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping ("/api")
public class PropertiesController {

 private final PropertyService propertyService;

 public PropertiesController (PropertyService propertyService) {
  this.propertyService = propertyService;
 }

 @GetMapping ("/property")
 public List<Property> listProperties () {
  return propertyService.listProperties();
 }

 @GetMapping ("/property/{name}")
 public Property getProperty (@PathVariable String name) {
  return propertyService.getProperty( name )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Property: " + name + " is not available" ) );
 }

 @PostMapping ("/property")
 public Property addProperty (@RequestBody Property property) {
  return propertyService.addProperty( property )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Could not add property: " + property.getName() ) );
 }

 @PutMapping ("/property")
 public Property updateProperty (@RequestBody Property property) {
  return propertyService.updateProperty( property )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Could not update property: " + property.getName() ) );
 }

 @DeleteMapping ("/property/{name}")
 public void deleteProperty (@PathVariable String name) {
  propertyService.deleteProperty( name )
          .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                  "Could not delete property: " + name ) );
 }
}
