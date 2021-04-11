package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.school.School;
import org.opensource.schulaltas.service.MapService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/api/v1/maps")
public class PublicMapsController {

 private final MapService mapService;

 public PublicMapsController (MapService mapService) {
  this.mapService = mapService;
 }

 @GetMapping
 public List<School> searchMapByBounds (
         @RequestParam (defaultValue = "") String northeast,
         @RequestParam (defaultValue = "") String southwest) {
  return mapService.searchMapByBounds( northeast, southwest );
 }

 @GetMapping ("/type/{type}")
 public List<School> searchMapByBoundsAndType (
         @PathVariable String type,
         @RequestParam (defaultValue = "") String northeast,
         @RequestParam (defaultValue = "") String southwest) {
  return mapService.searchMapByBoundsAndType( type, northeast, southwest );
 }

 @GetMapping ("/search/{search}")
 public List<School> searchMapByString (
         @PathVariable String search,
         @RequestParam (defaultValue = "") String northeast,
         @RequestParam (defaultValue = "") String southwest) {
  return mapService.searchMapByStringAndNearest( search, northeast, southwest );
 }
}
