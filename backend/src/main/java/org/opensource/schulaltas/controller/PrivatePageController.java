package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.controller.model.PageDto;
import org.opensource.schulaltas.model.website.Website;
import org.opensource.schulaltas.service.PageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping ("/auth/v1/page")
public class PrivatePageController {

 private final PageService pageService;

 public PrivatePageController (PageService pageService) {
  this.pageService = pageService;
 }

 @GetMapping
 public List<Website> listPages () {
  return pageService.listPages();
 }

 @GetMapping ("/slug/{slug}")
 public Website getPageBySlug (@PathVariable String slug) {
  return pageService.getPageBySlug( slug )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Page: " + slug + " is not available" ) );
 }

 @PostMapping
 public Website addPage (@RequestBody PageDto pageDto) {
  return pageService.addPage( pageDto )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Page: " + pageDto.getSlug() + " could not be added" ) );
 }

 @PutMapping ("/slug/{slug}/landingpage")
 public Website setLandingPageBySlug (@PathVariable String slug) {
  return pageService.setLandingPageBySlug( slug )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Could not set landing page: " + slug ) );
 }

 @PutMapping ("/slug/{slug}")
 public Website updatePage (@RequestBody PageDto pageDto, @PathVariable String slug) {
  return pageService.updatePage( pageDto, slug )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Could not update page: " + pageDto.getSlug() ) );
 }

 @DeleteMapping ("/slug/{slug}")
 public void deletePageBySlug (@PathVariable String slug) {
  pageService.deletePageBySlug( slug );
 }

}
