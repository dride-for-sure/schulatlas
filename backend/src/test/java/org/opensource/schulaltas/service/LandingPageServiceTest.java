package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.model.page.Page;
import org.opensource.schulaltas.repository.PageDb;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LandingPageServiceTest {

 private final PageDb pageDb = mock( PageDb.class );
 private final LandingPageService landingPageService = new LandingPageService( pageDb );

 private Page getPage (String name) {
  return Page.builder()
                 .name( name )
                 .updated( 1L )
                 .userId( "B" )
                 .landingPage( false )
                 .assemblies( List.of() )
                 .build();
 }

 @Test
 @DisplayName ("Set landing page should set landingPage to true, remove all other landingPages " +
                       "and return it as an optional")
 void setLandingPage () {
  // GIVEN
  when( pageDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ) ) );
  when( pageDb.findByLandingPageIs( true ) )
          .thenReturn( List.of( getPage( "B" ).toBuilder().landingPage( true ).build() ) );
  when( pageDb.save( getPage( "A" ).toBuilder().landingPage( true ).build() ) ).then( returnsFirstArg() );
  when( pageDb.save( getPage( "B" ).toBuilder().landingPage( false ).build() ) ).then( returnsFirstArg() );

  // WHEN
  Optional<Page> actual = landingPageService.setLandingPage( "A" );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  verify( pageDb ).save( getPage( "A" ).toBuilder().landingPage( true ).build() );
 }

 @Test
 @DisplayName ("Set landing page as landing page again should return it as optional")
 void setLandingPageAgainAsLandingPage () {
  // GIVEN
  when( pageDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  when( pageDb.findByLandingPageIs( true ) )
          .thenReturn( List.of( getPage( "B" ).toBuilder().landingPage( false ).build() ) );
  when( pageDb.save( getPage( "A" ).toBuilder().landingPage( true ).build() ) ).then( returnsFirstArg() );

  // WHEN
  Optional<Page> actual = landingPageService.setLandingPage( "A" );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  verify( pageDb ).save( getPage( "A" ).toBuilder().landingPage( true ).build() );
 }

 @Test
 @DisplayName ("Set a non existing landing page should return optional empty")
 void setNotExistingPageAsLandingPage () {
  // GIVEN
  when( pageDb.findById( "A" ) ).thenReturn( Optional.empty() );

  // WHEN
  Optional<Page> actual = landingPageService.setLandingPage( "NOTEXISTING" );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( pageDb, never() ).save( any() );
 }

 @Test
 @DisplayName ("Get landing pages should return the landing page")
 void getLandingPage () {
  // GIVEN
  when( pageDb.findByLandingPageIs( true ) )
          .thenReturn( List.of( getPage( "A" ).toBuilder().landingPage( true ).build() ) );

  // WHEN
  Page actual = landingPageService.getLandingPage();

  // THEN
  assertThat( actual, is( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
 }
}
