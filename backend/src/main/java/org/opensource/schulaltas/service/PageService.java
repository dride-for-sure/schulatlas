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
 private final TimeUTC timeUTC;

 public PageService (PageDb pageDb, TimeUTC timeUTC) {
  this.pageDb = pageDb;
  this.timeUTC = timeUTC;
 }

 public List<Page> listPages () {
  return pageDb.findAll();
 }

 public Optional<Page> getPage (String name) {
  return pageDb.findById( name );
 }

 public Page addPage (PageDto pageDto) {
  Optional<Page> page = pageDb.findById( pageDto.getName() );
  if ( page.isEmpty() ) {
   Page newPage = Page.builder()
                          .name( pageDto.getName() )
                          .updated( timeUTC.now() )
                          .userId( pageDto.getUserId() )
                          .components( pageDto.getComponents() )
                          .build();
   return pageDb.save( newPage );
  }
  return page.get();
 }

 public Optional<Page> updatePage (PageDto pageDto) {
  Optional<Page> pageToUpdate = pageDb.findById( pageDto.getName() );
  if ( pageToUpdate.isPresent() ) {
   Page updatedPage = pageToUpdate.get()
                              .toBuilder()
                              .updated( timeUTC.now() )
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
