package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.page.Page;
import org.opensource.schulaltas.service.LandingPageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping ("/auth/landingpage")
public class PrivateLandingPageController {


 private final LandingPageService landingPageService;

 public PrivateLandingPageController (LandingPageService landingPageService) {
  this.landingPageService = landingPageService;
 }

 @GetMapping
 public Page getLandingPage () {
  return landingPageService.getLandingPage();
 }

 @PutMapping ("/{name}")
 public Page setLandingPage (@PathVariable String name) {
  return landingPageService.setLandingPage( name )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Could not set landing page: " + name ) );
 }
}
