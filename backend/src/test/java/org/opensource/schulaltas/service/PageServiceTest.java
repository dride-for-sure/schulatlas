package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.controller.model.PageDto;
import org.opensource.schulaltas.model.page.Page;
import org.opensource.schulaltas.repository.PageDb;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

class PageServiceTest {

 private final PageDb pageDb = mock( PageDb.class );
 private final TimeUTC timeUTC = mock( TimeUTC.class );
 private final AssemblyService assemblyService = mock( AssemblyService.class );
 private final PageService pageService = new PageService( pageDb, timeUTC, assemblyService );

 private Page getPage (String slug) {
  return Page.builder()
                 .slug( slug )
                 .updated( 1L )
                 .userId( "B" )
                 .landingPage( false )
                 .assemblies( List.of() )
                 .build();
 }

 @Test
 @DisplayName ("List pages should return all pages in the db")
 void listPages () {
  // GIVEN
  when( pageDb.findAll() ).thenReturn( List.of( getPage( "A" ), getPage( "B" ) ) );

  // WHEN
  List<Page> actual = pageService.listPages();

  // THEN
  assertThat( actual, containsInAnyOrder( getPage( "A" ), getPage( "B" ) ) );
 }

 @Test
 @DisplayName ("Get pages should return a specific page")
 void getPageBySlug () {
  // GIVEN
  when( pageDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ) ) );

  // WHEN
  Optional<Page> actual = pageService.getPageBySlug( "A" );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ) ) );
 }

 @Test
 @DisplayName ("Get pages should return an optional.empty for a non existing page")
 void getNotExistingPageBySlug () {
  // GIVEN
  when( pageDb.findById( "A" ) ).thenReturn( Optional.empty() );

  // WHEN
  Optional<Page> actual = pageService.getPageBySlug( "A" );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
 }

 @Test
 @DisplayName ("Add page should add the page to the db and return optional of page")
 void addPage () {
  // GIVEN
  PageDto pageDto = PageDto.builder()
                            .slug( "A" )
                            .userId( "B" )
                            .assemblies( List.of() )
                            .build();
  when( pageDb.findById( "A" ) ).thenReturn( Optional.empty() );
  when( pageDb.save( getPage( "A" ) ) ).thenReturn( getPage( "A" ) );
  when( timeUTC.now() ).thenReturn( 1L );
  when( assemblyService.hasAvailableAssemblies( pageDto.getAssemblies() ) ).thenReturn( true );

  // WHEN
  Optional<Page> actual = pageService.addPage( pageDto );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ) ) );
  verify( pageDb ).save( getPage( "A" ) );
 }

 @Test
 @DisplayName ("Add page should return optional empty if assembly is invalid")
 void addPageWithInvalidAssembly () {
  // GIVEN
  PageDto pageDto = PageDto.builder()
                            .slug( "A" )
                            .userId( "B" )
                            .assemblies( List.of() )
                            .build();
  when( pageDb.findById( "A" ) ).thenReturn( Optional.empty() );
  when( assemblyService.hasAvailableAssemblies( pageDto.getAssemblies() ) ).thenReturn( false );

  // WHEN
  Optional<Page> actual = pageService.addPage( pageDto );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( pageDb, never() ).save( getPage( "A" ) );
 }

 @Test
 @DisplayName ("Add existing page should return the already existing page as optional")
 void addExistingPage () {
  // GIVEN
  PageDto pageDto = PageDto.builder()
                            .slug( "A" )
                            .userId( "B" )
                            .assemblies( List.of() )
                            .build();
  when( pageDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ) ) );
  when( pageDb.save( getPage( "A" ) ) ).thenReturn( getPage( "A" ) );
  when( timeUTC.now() ).thenReturn( 1L );
  when( assemblyService.hasAvailableAssemblies( pageDto.getAssemblies() ) ).thenReturn( true );

  // WHEN
  Optional<Page> actual = pageService.addPage( pageDto );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ) ) );
  verify( pageDb, never() ).save( getPage( "A" ) );
 }

 @Test
 @DisplayName ("Update existing page should update the page in the db")
 void updatePage () {
  // GIVEN
  PageDto pageDto = PageDto.builder()
                            .slug( "A" )
                            .userId( "B" )
                            .assemblies( List.of() )
                            .build();
  when( pageDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ) ) );
  when( pageDb.save( getPage( "A" ) ) ).thenReturn( getPage( "A" ) );
  when( timeUTC.now() ).thenReturn( 1L );
  when( assemblyService.hasAvailableAssemblies( pageDto.getAssemblies() ) ).thenReturn( true );

  // WHEN
  Optional<Page> actual = pageService.updatePage( pageDto );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ) ) );
  verify( pageDb ).save( getPage( "A" ) );
 }

 @Test
 @DisplayName ("Update existing page should return optional empty if assemblies are invalid")
 void updatePageWithInvalidAssembly () {
  // GIVEN
  PageDto pageDto = PageDto.builder()
                            .slug( "A" )
                            .userId( "B" )
                            .assemblies( List.of() )
                            .build();
  when( pageDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ) ) );
  when( assemblyService.hasAvailableAssemblies( pageDto.getAssemblies() ) ).thenReturn( false );

  // WHEN
  Optional<Page> actual = pageService.updatePage( pageDto );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( pageDb, never() ).save( getPage( "A" ) );
 }

 @Test
 @DisplayName ("Update page should return an optional.empty if page doesnt exist")
 void updateNotExistingPage () {
  // GIVEN
  PageDto pageDto = PageDto.builder()
                            .slug( "A" )
                            .userId( "B" )
                            .assemblies( List.of() )
                            .build();
  when( pageDb.findById( "A" ) ).thenReturn( Optional.empty() );
  when( pageDb.save( getPage( "A" ) ) ).thenReturn( getPage( "A" ) );
  when( timeUTC.now() ).thenReturn( 1L );

  // WHEN
  Optional<Page> actual = pageService.updatePage( pageDto );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( pageDb, never() ).save( getPage( "A" ) );
 }

 @Test
 @DisplayName ("Set landing page should set landingPage to true, remove all other landingPages " +
                       "and return it as an optional")
 void setLandingPageBySlug () {
  // GIVEN
  when( pageDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ) ) );
  when( pageDb.findByLandingPageIs( true ) )
          .thenReturn( List.of( getPage( "B" ).toBuilder().landingPage( true ).build() ) );
  when( pageDb.save( getPage( "A" ).toBuilder().landingPage( true ).build() ) ).then( returnsFirstArg() );
  when( pageDb.save( getPage( "B" ).toBuilder().landingPage( false ).build() ) ).then( returnsFirstArg() );

  // WHEN
  Optional<Page> actual = pageService.setLandingPageBySlug( "A" );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  verify( pageDb ).save( getPage( "A" ).toBuilder().landingPage( true ).build() );
 }

 @Test
 @DisplayName ("Set landing page as landing page again should return it as optional")
 void setLandingPageAgainAsLandingPageBySlug () {
  // GIVEN
  when( pageDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  when( pageDb.findByLandingPageIs( true ) )
          .thenReturn( List.of( getPage( "B" ).toBuilder().landingPage( false ).build() ) );
  when( pageDb.save( getPage( "A" ).toBuilder().landingPage( true ).build() ) ).then( returnsFirstArg() );

  // WHEN
  Optional<Page> actual = pageService.setLandingPageBySlug( "A" );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  verify( pageDb ).save( getPage( "A" ).toBuilder().landingPage( true ).build() );
 }

 @Test
 @DisplayName ("Set a non existing landing page should return optional empty")
 void setNotExistingPageAsLandingPageBySlug () {
  // GIVEN
  when( pageDb.findById( "A" ) ).thenReturn( Optional.empty() );

  // WHEN
  Optional<Page> actual = pageService.setLandingPageBySlug( "NOTEXISTING" );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( pageDb, never() ).save( any() );
 }

 @Test
 @DisplayName ("Delete page should call deleteById on the db")
 void deletePageBySlug () {
  // GIVEN
  // WHEN
  pageService.deletePageBySlug( "A" );

  // THEN
  verify( pageDb ).deleteById( "A" );
 }
}