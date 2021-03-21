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
import static org.mockito.Mockito.*;

class PageServiceTest {

 private final PageDb pageDb = mock( PageDb.class );
 private final TimeUTC timeUTC = mock( TimeUTC.class );
 private final PageService pageService = new PageService( pageDb, timeUTC );

 private Page getPage (String name) {
  return Page.builder()
                 .name( name )
                 .updated( 1L )
                 .userId( "B" )
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
 void getPage () {
  // GIVEN
  when( pageDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ) ) );

  // WHEN
  Optional<Page> actual = pageService.getPage( "A" );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ) ) );
 }

 @Test
 @DisplayName ("Get pages should return an optional.empty for a non existing page")
 void getNotExistingPage () {
  // GIVEN
  when( pageDb.findById( "A" ) ).thenReturn( Optional.empty() );

  // WHEN
  Optional<Page> actual = pageService.getPage( "A" );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
 }

 @Test
 @DisplayName ("Add page should add the page to the db")
 void addPage () {
  // GIVEN
  PageDto pageDto = PageDto.builder()
                            .name( "A" )
                            .userId( "B" )
                            .assemblies( List.of() )
                            .build();
  when( pageDb.findById( "A" ) ).thenReturn( Optional.empty() );
  when( pageDb.save( getPage( "A" ) ) ).thenReturn( getPage( "A" ) );
  when( timeUTC.now() ).thenReturn( 1L );

  // WHEN
  Page actual = pageService.addPage( pageDto );

  // THEN
  assertThat( actual, is( getPage( "A" ) ) );
  verify( pageDb ).save( getPage( "A" ) );
 }

 @Test
 @DisplayName ("Add page should return the already existing page")
 void addExistingPage () {
  // GIVEN
  PageDto pageDto = PageDto.builder()
                            .name( "A" )
                            .userId( "B" )
                            .assemblies( List.of() )
                            .build();
  when( pageDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ) ) );
  when( pageDb.save( getPage( "A" ) ) ).thenReturn( getPage( "A" ) );
  when( timeUTC.now() ).thenReturn( 1L );

  // WHEN
  Page actual = pageService.addPage( pageDto );

  // THEN
  assertThat( actual, is( getPage( "A" ) ) );
  verify( pageDb, never() ).save( getPage( "A" ) );
 }

 @Test
 @DisplayName ("Update page should update the page in the db")
 void updatePage () {
  // GIVEN
  PageDto pageDto = PageDto.builder()
                            .name( "A" )
                            .userId( "B" )
                            .assemblies( List.of() )
                            .build();
  when( pageDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ) ) );
  when( pageDb.save( getPage( "A" ) ) ).thenReturn( getPage( "A" ) );
  when( timeUTC.now() ).thenReturn( 1L );

  // WHEN
  Optional<Page> actual = pageService.updatePage( pageDto );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ) ) );
  verify( pageDb ).save( getPage( "A" ) );
 }

 @Test
 @DisplayName ("Update page should return an optional.empty if page doesnt exist")
 void updateNotExistingPage () {
  // GIVEN
  PageDto pageDto = PageDto.builder()
                            .name( "A" )
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
 @DisplayName ("Delete page should call deleteById on the db")
 void deletePage () {
  // GIVEN
  // WHEN
  pageService.deletePage( "A" );

  // THEN
  verify( pageDb ).deleteById( "A" );
 }
}