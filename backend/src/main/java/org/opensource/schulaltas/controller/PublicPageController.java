package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.page.Page;
import org.opensource.schulaltas.service.PageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping ("/api/v1/page")
public class PublicPageController {

 private final PageService pageService;

 public PublicPageController (PageService pageService) {
  this.pageService = pageService;
 }

 @GetMapping ("/name/{name}")
 public Page getPageByName (@PathVariable String name) {
  return pageService.getPageByName( name )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Page: " + name + " is not available" ) );
 }
}
