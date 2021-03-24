package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.controller.model.PageDto;
import org.opensource.schulaltas.model.page.Page;
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
 public List<Page> listPages () {
  return pageService.listPages();
 }

 @GetMapping ("/name/{name}")
 public Page getPageByName (@PathVariable String name) {
  return pageService.getPageByName( name )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Page: " + name + " is not available" ) );
 }

 @PostMapping
 public Page addPage (@RequestBody PageDto pageDto) {
  return pageService.addPage( pageDto )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Page: " + pageDto.getName() + " could not be added" ) );
 }

 @PutMapping ("/name/{name}/landingpage")
 public Page setLandingPageByName (@PathVariable String name) {
  return pageService.setLandingPageByName( name )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Could not set landing page: " + name ) );
 }

 @PutMapping ("/name/{name}")
 public Page updatePage (@RequestBody PageDto pageDto) {
  return pageService.updatePage( pageDto )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Could not update page: " + pageDto.getName() ) );
 }

 @DeleteMapping ("/name/{name}")
 public void deletePageByName (@PathVariable String name) {
  pageService.deletePageByName( name );
 }

}
