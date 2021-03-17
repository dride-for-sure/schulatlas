package org.opensource.schulaltas.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PageService {

 private final PageDb pageDb;

 public PageService (PageDb pageDb) {
  this.pageDb = pageDb;
 }

 public List<Page> listPages () {
  return pageDb.findAll();
 }

 public Optional<Page> getPage (String name) {
  return pageDb.findById( name );
 }

 public Optional<Page> addPage (PageDto pageDto) {
  return pageDb.save( pageDto );
 }

 public Optional<Page> updatePage (PageDto pageDto) {
  return pageDb.save( pageDto );
 }

}
