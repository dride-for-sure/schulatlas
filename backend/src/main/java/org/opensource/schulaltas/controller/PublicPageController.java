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

 @GetMapping ("/slug/{slug}")
 public Page getPageBySlug (@PathVariable String slug) {
  return pageService.getPageBySlug( slug )
                 .orElseThrow( () -> new ResponseStatusException( HttpStatus.BAD_REQUEST,
                         "Page: " + slug + " is not available" ) );
 }
}
