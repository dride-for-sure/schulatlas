package org.opensource.schulaltas.controller;

import org.opensource.schulaltas.model.page.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping ("/api/page")
public class PublicPageController {

 private final PageService pageService;

 public PublicPageController (PageService pageService) {
  this.pageService = pageService;
 }

 @GetMapping
 public List<Page> listPages () {
  return pageService.listPages();
 }

 @GetMapping ("/{name}")
 public Page getPage (String name) {
  return pageService.getPage( name )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Page: " + name + " is not available" ) );
 }
}
