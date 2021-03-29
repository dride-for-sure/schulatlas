package org.opensource.schulaltas.service;

import org.opensource.schulaltas.controller.model.PageDto;
import org.opensource.schulaltas.model.website.Website;
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

 public List<Website> listPages () {
  return pageDb.findAll();
 }

 public Optional<Website> getPageBySlug (String slug) {
  return pageDb.findById( slug );
 }

 public Optional<Website> addPage (PageDto pageDto) {
  Optional<Website> page = pageDb.findById( pageDto.getSlug() );
  if ( page.isEmpty() ) {
   if ( assemblyService.hasAvailableAssemblies( pageDto.getAssemblies() ) ) {
    Website newWebsite = Website.builder()
                                 .slug( pageDto.getSlug() )
                                 .updated( timeUTC.now() )
                                 .userId( pageDto.getUserId() )
                                 .landingPage( false )
                                 .assemblies( pageDto.getAssemblies() )
                                 .build();
    return Optional.of( pageDb.save( newWebsite ) );
   }
   return Optional.empty();
  }
  return page;
 }

 public Optional<Website> updatePage (PageDto pageDto, String slug) {
  Optional<Website> pageToUpdate = pageDb.findById( slug );
  if ( pageToUpdate.isPresent() && assemblyService.hasAvailableAssemblies( pageDto.getAssemblies() ) ) {
   Website updatedWebsite = pageToUpdate.get()
                                    .toBuilder()
                                    .slug( pageDto.getSlug() )
                                    .updated( timeUTC.now() )
                                    .userId( pageDto.getUserId() )
                                    .assemblies( pageDto.getAssemblies() )
                                    .build();
   pageDb.deleteById( slug );
   return Optional.of( pageDb.save( updatedWebsite ) );
  }
  return Optional.empty();
 }

 public Optional<Website> setLandingPageBySlug (String slug) {
  Optional<Website> pageToSetLandingPage = pageDb.findById( slug );
  if ( pageToSetLandingPage.isPresent() ) {
   List<Website> landingWebsites = pageDb.findByLandingPageIs( true );
   landingWebsites.forEach( page -> pageDb.save( page.toBuilder().landingPage( false ).build() ) );
   Website newLandingWebsite = pageToSetLandingPage.get()
                                       .toBuilder()
                                       .landingPage( true )
                                       .build();
   return Optional.of( pageDb.save( newLandingWebsite ) );
  }
  return Optional.empty();
 }

 public void deletePageBySlug (String slug) {
  pageDb.deleteById( slug );
 }
}
