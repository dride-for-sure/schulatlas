package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.controller.model.WebsiteDto;
import org.opensource.schulaltas.model.website.Website;
import org.opensource.schulaltas.service.WebsiteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping ("/auth/v1/page")
public class PrivateWebsiteController {

 private final WebsiteService websiteService;

 public PrivateWebsiteController (WebsiteService websiteService) {
  this.websiteService = websiteService;
 }

 @GetMapping
 public List<Website> listWebsites () {
  return websiteService.listWebsites();
 }

 @GetMapping ("/slug/{slug}")
 public Website getWebsiteBySlug (@PathVariable String slug) {
  return websiteService.getWebsiteBySlug( slug )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Page: " + slug + " is not available" ) );
 }

 @PostMapping
 public Website addWebsite (@RequestBody WebsiteDto websiteDto) {
  return websiteService.addWebsite( websiteDto )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Page: " + websiteDto.getSlug() + " could not be added" ) );
 }

 @PutMapping ("/slug/{slug}/landingpage")
 public Website setLandingPageBySlug (@PathVariable String slug) {
  return websiteService.setLandingPageBySlug( slug )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Could not set landing page: " + slug ) );
 }

 @PutMapping ("/slug/{slug}")
 public Website updateWebsite (@RequestBody WebsiteDto websiteDto, @PathVariable String slug) {
  return websiteService.updateWebsite( websiteDto, slug )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Could not update page: " + websiteDto.getSlug() ) );
 }

 @DeleteMapping ("/slug/{slug}")
 public void deleteWebsiteBySlug (@PathVariable String slug) {
  websiteService.deleteWebsiteBySlug( slug );
 }

}
