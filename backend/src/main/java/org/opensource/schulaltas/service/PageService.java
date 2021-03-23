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
 private final AssemblyService assemblyService;

 public PageService (PageDb pageDb, TimeUTC timeUTC, AssemblyService assemblyService) {
  this.pageDb = pageDb;
  this.timeUTC = timeUTC;
  this.assemblyService = assemblyService;
 }

 public List<Page> listPages () {
  return pageDb.findAll();
 }

 public Optional<Page> getPageByName (String name) {
  return pageDb.findById( name );
 }

 public Optional<Page> addPage (PageDto pageDto) {
  Optional<Page> page = pageDb.findById( pageDto.getName() );
  if ( page.isEmpty() ) {
   if ( assemblyService.hasAvailableAssemblies( pageDto.getAssemblies() ) ) {
    Page newPage = Page.builder()
                           .name( pageDto.getName() )
                           .updated( timeUTC.now() )
                           .userId( pageDto.getUserId() )
                           .assemblies( pageDto.getAssemblies() )
                           .build();
    return Optional.of( pageDb.save( newPage ) );
   }
   return Optional.empty();
  }
  return page;
 }

 public Optional<Page> updatePage (PageDto pageDto) {
  Optional<Page> pageToUpdate = pageDb.findById( pageDto.getName() );
  if ( pageToUpdate.isPresent() && assemblyService.hasAvailableAssemblies( pageDto.getAssemblies() ) ) {
   Page updatedPage = pageToUpdate.get()
                              .toBuilder()
                              .updated( timeUTC.now() )
                              .userId( pageDto.getUserId() )
                              .assemblies( pageDto.getAssemblies() )
                              .build();
   return Optional.of( pageDb.save( updatedPage ) );
  }
  return Optional.empty();
 }

 public Optional<Page> setLandingPageByName (String name) {
  Optional<Page> pageToSetLandingPage = pageDb.findById( name );
  if ( pageToSetLandingPage.isPresent() ) {
   List<Page> landingPages = pageDb.findByLandingPageIs( true );
   landingPages.forEach( page -> pageDb.save( page.toBuilder().landingPage( false ).build() ) );
   Page newLandingPage = pageToSetLandingPage.get()
                                 .toBuilder()
                                 .landingPage( true )
                                 .build();
   return Optional.of( pageDb.save( newLandingPage ) );
  }
  return Optional.empty();
 }

 public void deletePageByName (String name) {
  pageDb.deleteById( name );
 }
}
