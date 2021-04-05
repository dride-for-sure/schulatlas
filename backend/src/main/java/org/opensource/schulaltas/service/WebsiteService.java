package org.opensource.schulaltas.service;

import org.opensource.schulaltas.controller.model.WebsiteDto;
import org.opensource.schulaltas.model.website.Website;
import org.opensource.schulaltas.repository.WebsiteDb;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WebsiteService {

 private final WebsiteDb websiteDb;
 private final TimeUTC timeUTC;
 private final AssemblyService assemblyService;

 public WebsiteService (WebsiteDb websiteDb, TimeUTC timeUTC, AssemblyService assemblyService) {
  this.websiteDb = websiteDb;
  this.timeUTC = timeUTC;
  this.assemblyService = assemblyService;
 }

 public List<Website> listWebsites () {
  return websiteDb.findAll();
 }

 public Optional<Website> getWebsiteBySlug (String slug) {
  return websiteDb.findById( slug );
 }

 public Optional<Website> addWebsite (WebsiteDto websiteDto) {
  Optional<Website> website = websiteDb.findById( websiteDto.getSlug() );
  if ( website.isEmpty() ) {
   if ( assemblyService.hasAvailableAssemblies( websiteDto.getAssemblies() ) ) {
    Website newWebsite = Website.builder()
                                 .slug( websiteDto.getSlug() )
                                 .updated( timeUTC.now() )
                                 .userId( websiteDto.getUserId() )
                                 .landingPage( false )
                                 .assemblies( websiteDto.getAssemblies() )
                                 .build();
    return Optional.of( websiteDb.save( newWebsite ) );
   }
   return Optional.empty();
  }
  return website;
 }

 public Optional<Website> updateWebsite (WebsiteDto websiteDto, String slug) {
  Optional<Website> websiteToUpdate = websiteDb.findById( slug );
  if ( websiteToUpdate.isPresent() && assemblyService.hasAvailableAssemblies( websiteDto.getAssemblies() ) ) {
   Website updatedWebsite = websiteToUpdate.get()
                                    .toBuilder()
                                    .slug( websiteDto.getSlug() )
                                    .updated( timeUTC.now() )
                                    .userId( websiteDto.getUserId() )
                                    .assemblies( websiteDto.getAssemblies() )
                                    .build();
   websiteDb.deleteById( slug );
   return Optional.of( websiteDb.save( updatedWebsite ) );
  }
  return Optional.empty();
 }

 public Optional<Website> setLandingPageBySlug (String slug) {
  Optional<Website> websiteToSetLandingPage = websiteDb.findById( slug );
  if ( websiteToSetLandingPage.isPresent() ) {
   List<Website> landingWebsites = websiteDb.findByLandingPageIs( true );
   landingWebsites.forEach( page -> websiteDb.save( page.toBuilder().landingPage( false ).build() ) );
   Website newLandingWebsite = websiteToSetLandingPage.get()
                                       .toBuilder()
                                       .landingPage( true )
                                       .build();
   return Optional.of( websiteDb.save( newLandingWebsite ) );
  }
  return Optional.empty();
 }

 public void deleteWebsiteBySlug (String slug) {
  websiteDb.deleteById( slug );
 }
}
