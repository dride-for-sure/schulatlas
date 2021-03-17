package org.opensource.schulaltas.service;

import org.opensource.schulaltas.controller.model.PageDto;
import org.opensource.schulaltas.model.page.Page;
import org.opensource.schulaltas.repository.PageDb;
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

 public Page addPage (PageDto pageDto) {
  Page page = Page.builder()
                      .name( pageDto.getName() )
                      .updated( System.currentTimeMillis() )
                      .userId( pageDto.getUserId() )
                      .components( pageDto.getComponents() )
                      .build();
  return pageDb.save( page );
 }

 public Optional<Page> updatePage (PageDto pageDto) {
  Optional<Page> pageToUpdate = pageDb.findById( pageDto.getName() );
  if ( pageToUpdate.isPresent() ) {
   Page updatedPage = pageToUpdate.get()
                              .toBuilder()
                              .updated( System.currentTimeMillis() )
                              .userId( pageDto.getUserId() )
                              .components( pageDto.getComponents() )
                              .build();
   return Optional.of( pageDb.save( updatedPage ) );
  }
  return Optional.empty();
 }

 public void deletePage (String name) {
  pageDb.deleteById( name );
 }
}
