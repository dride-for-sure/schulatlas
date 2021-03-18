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
 private final TimeUtil timeUtil = mock( TimeUtil.class );
 private final PageService pageService = new PageService( pageDb, timeUtil );

 @Test
 @DisplayName ("List pages should return all pages in the db")
 void listPages () {
  // GIVEN
  Page page1 = Page.builder()
                       .name( "A" )
                       .updated( 1L )
                       .userId( "B" )
                       .components( List.of() )
                       .build();
  Page page2 = Page.builder()
                       .name( "B" )
                       .updated( 1L )
                       .userId( "B" )
                       .components( List.of() )
                       .build();
  when( pageDb.findAll() ).thenReturn( List.of( page1, page2 ) );

  // WHEN
  List<Page> actual = pageService.listPages();

  // THEN
  assertThat( actual, containsInAnyOrder(
          page1.toBuilder().build(),
          page2.toBuilder().build() ) );
 }

 @Test
 @DisplayName ("Get pages should return a specific page")
 void getPage () {
  // GIVEN
  Page page = Page.builder()
                      .name( "A" )
                      .updated( 1L )
                      .userId( "B" )
                      .components( List.of() )
                      .build();
  when( pageDb.findById( page.getName() ) ).thenReturn( Optional.of( page ) );

  // WHEN
  Optional<Page> actual = pageService.getPage( page.getName() );

  // THEN
  assertThat( actual.get(), is( page.toBuilder().build() ) );
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
                            .components( List.of() )
                            .build();
  Page page = Page.builder()
                      .name( "A" )
                      .updated( 1L )
                      .userId( "B" )
                      .components( List.of() )
                      .build();
  when( pageDb.findById( "A" ) ).thenReturn( Optional.empty() );
  when( pageDb.save( page ) ).thenReturn( page );
  when( timeUtil.now() ).thenReturn( 1L );

  // WHEN
  Page actual = pageService.addPage( pageDto );

  // THEN
  assertThat( actual, is( page.toBuilder().build() ) );
  verify( pageDb ).save( page );
 }

 @Test
 @DisplayName ("Add page should return an optional.empty for an already existing page")
 void addExistingPage () {
  // GIVEN
  PageDto pageDto = PageDto.builder()
                            .name( "A" )
                            .userId( "B" )
                            .components( List.of() )
                            .build();
  Page page = Page.builder()
                      .name( "A" )
                      .updated( 1L )
                      .userId( "B" )
                      .components( List.of() )
                      .build();
  when( pageDb.findById( "A" ) ).thenReturn( Optional.of( page ) );
  when( pageDb.save( page ) ).thenReturn( page );
  when( timeUtil.now() ).thenReturn( 1L );

  // WHEN
  Page actual = pageService.addPage( pageDto );

  // THEN
  assertThat( actual, is( page.toBuilder().build() ) );
  verify( pageDb, never() ).save( page );
 }

 @Test
 @DisplayName ("Update page should update the page in the db")
 void updatePage () {
  // GIVEN
  PageDto pageDto = PageDto.builder()
                            .name( "A" )
                            .userId( "B" )
                            .components( List.of() )
                            .build();
  Page page = Page.builder()
                      .name( "A" )
                      .updated( 1L )
                      .userId( "B" )
                      .components( List.of() )
                      .build();
  when( pageDb.findById( "A" ) ).thenReturn( Optional.of( page ) );
  when( pageDb.save( page ) ).thenReturn( page );
  when( timeUtil.now() ).thenReturn( 1L );

  // WHEN
  Optional<Page> actual = pageService.updatePage( pageDto );

  // THEN
  assertThat( actual.get(), is( page.toBuilder().build() ) );
  verify( pageDb ).save( page );
 }

 @Test
 @DisplayName ("Update page should return an optional.empty if page doesnt exist")
 void updateNotExistingPage () {
  // GIVEN
  PageDto pageDto = PageDto.builder()
                            .name( "A" )
                            .userId( "B" )
                            .components( List.of() )
                            .build();
  Page page = Page.builder()
                      .name( "A" )
                      .updated( 1L )
                      .userId( "B" )
                      .components( List.of() )
                      .build();
  when( pageDb.findById( "A" ) ).thenReturn( Optional.empty() );
  when( pageDb.save( page ) ).thenReturn( page );
  when( timeUtil.now() ).thenReturn( 1L );

  // WHEN
  Optional<Page> actual = pageService.updatePage( pageDto );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( pageDb, never() ).save( page );
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