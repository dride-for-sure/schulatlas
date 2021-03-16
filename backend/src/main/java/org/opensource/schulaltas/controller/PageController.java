package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.controller.model.LandingPageDto;
import org.opensource.schulaltas.controller.model.PageDto;
import org.opensource.schulaltas.model.page.LandingPage;
import org.opensource.schulaltas.model.page.Page;
import org.opensource.schulaltas.service.PageService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping ("/api")
public class PageController {

 private final PageService pageService;

 public PageController (PageService pageService) {
  this.pageService = pageService;
 }

 @GetMapping ("/page")
 public List<Page> listPages () {
  return pageService.listPages();
 }

 @GetMapping ("/landing-page")
 public LandingPage getLandingPage () {
  return pageService.getLandingPage()
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Landing page is not available" ) );
 }

 @GetMapping ("/page/{name}")
 public Page getPage (String name) {
  return pageService.getPage( name )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Page: " + name + " is not available" ) );
 }

 @PostMapping ("/page")
 public Page addPage (@RequestBody PageDto pageDto) {
  return pageService.addPage( pageDto )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Could not add page: " + pageDto.getName() ) );
 }

 @PutMapping ("/page")
 public Page updatePage (@RequestBody PageDto pageDto) {
  return pageService.updatePage( pageDto )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Could not update page: " + pageDto.getName() ) );
 }

 @PutMapping ("/landing-page")
 public LandingPage updateLandingPage (@RequestBody LandingPageDto landingPageDto) {
  return pageService.updateLandingPage( landingPageDto )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Could not update landing page" ) );
 }

 @DeleteMapping ("/page/{name}")
 public void deletePage (@PathVariable String name) {
  return pageService.deletePage( name )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Could not delete page: " + name ) );
 }

}
