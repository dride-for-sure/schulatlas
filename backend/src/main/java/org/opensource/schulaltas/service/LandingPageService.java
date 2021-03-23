package org.opensource.schulaltas.service;

import org.opensource.schulaltas.model.page.Page;
import org.opensource.schulaltas.repository.PageDb;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LandingPageService {

 private final PageDb pageDb;

 public LandingPageService (PageDb pageDb) {
  this.pageDb = pageDb;
 }

 public Optional<Page> setLandingPage (String name) {
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

 public Page getLandingPage () {
  return pageDb.findByLandingPageIs( true ).get( 0 );
 }


}
