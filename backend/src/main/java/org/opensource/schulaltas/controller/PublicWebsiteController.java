package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.website.Website;
import org.opensource.schulaltas.service.WebsiteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping ("/api/v1/page")
public class PublicWebsiteController {

 private final WebsiteService websiteService;

 public PublicWebsiteController (WebsiteService websiteService) {
  this.websiteService = websiteService;
 }

 @GetMapping ("/slug/{slug}")
 public Website getWebsiteBySlug (@PathVariable String slug) {
  return websiteService.getWebsiteBySlug( slug )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Page: " + slug + " is not available" ) );
 }

 @GetMapping ("/landingpage")
 public Website getLandingPage () {
  return websiteService.getLandingPage()
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Landing Page is not available" ) );
 }
}
