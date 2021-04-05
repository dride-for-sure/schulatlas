package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.controller.model.WebsiteDto;
import org.opensource.schulaltas.model.website.Website;
import org.opensource.schulaltas.repository.WebsiteDb;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

class WebsiteServiceTest {

 private final WebsiteDb websiteDb = mock( WebsiteDb.class );
 private final TimeUTC timeUTC = mock( TimeUTC.class );
 private final AssemblyService assemblyService = mock( AssemblyService.class );
 private final WebsiteService websiteService = new WebsiteService( websiteDb, timeUTC, assemblyService );

 private Website getPage (String slug) {
  return Website.builder()
                 .slug( slug )
                 .updated( 1L )
                 .userId( "B" )
                 .landingPage( false )
                 .assemblies( List.of() )
                 .build();
 }

 @Test
 @DisplayName ("List pages should return all pages in the db")
 void listWebsites () {
  // GIVEN
  when( websiteDb.findAll() ).thenReturn( List.of( getPage( "A" ), getPage( "B" ) ) );

  // WHEN
  List<Website> actual = websiteService.listWebsites();

  // THEN
  assertThat( actual, containsInAnyOrder( getPage( "A" ), getPage( "B" ) ) );
 }

 @Test
 @DisplayName ("Get pages should return a specific page")
 void getWebsiteBySlug () {
  // GIVEN
  when( websiteDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ) ) );

  // WHEN
  Optional<Website> actual = websiteService.getWebsiteBySlug( "A" );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ) ) );
 }

 @Test
 @DisplayName ("Get pages should return an optional.empty for a non existing page")
 void getNotExistingWebsiteBySlug () {
  // GIVEN
  when( websiteDb.findById( "A" ) ).thenReturn( Optional.empty() );

  // WHEN
  Optional<Website> actual = websiteService.getWebsiteBySlug( "A" );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
 }

 @Test
 @DisplayName ("Add page should add the page to the db and return optional of page")
 void addWebsite () {
  // GIVEN
  WebsiteDto websiteDto = WebsiteDto.builder()
                                  .slug( "A" )
                                  .userId( "B" )
                                  .assemblies( List.of() )
                                  .build();
  when( websiteDb.findById( "A" ) ).thenReturn( Optional.empty() );
  when( websiteDb.save( getPage( "A" ) ) ).thenReturn( getPage( "A" ) );
  when( timeUTC.now() ).thenReturn( 1L );
  when( assemblyService.hasAvailableAssemblies( websiteDto.getAssemblies() ) ).thenReturn( true );

  // WHEN
  Optional<Website> actual = websiteService.addWebsite( websiteDto );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ) ) );
  verify( websiteDb ).save( getPage( "A" ) );
 }

 @Test
 @DisplayName ("Add page should return optional empty if assembly is invalid")
 void addWebsiteWithInvalidAssembly () {
  // GIVEN
  WebsiteDto websiteDto = WebsiteDto.builder()
                                  .slug( "A" )
                                  .userId( "B" )
                                  .assemblies( List.of() )
                                  .build();
  when( websiteDb.findById( "A" ) ).thenReturn( Optional.empty() );
  when( assemblyService.hasAvailableAssemblies( websiteDto.getAssemblies() ) ).thenReturn( false );

  // WHEN
  Optional<Website> actual = websiteService.addWebsite( websiteDto );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( websiteDb, never() ).save( getPage( "A" ) );
 }

 @Test
 @DisplayName ("Add existing page should return the already existing page as optional")
 void addExistingWebsite () {
  // GIVEN
  WebsiteDto websiteDto = WebsiteDto.builder()
                                  .slug( "A" )
                                  .userId( "B" )
                                  .assemblies( List.of() )
                                  .build();
  when( websiteDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ) ) );
  when( websiteDb.save( getPage( "A" ) ) ).thenReturn( getPage( "A" ) );
  when( timeUTC.now() ).thenReturn( 1L );
  when( assemblyService.hasAvailableAssemblies( websiteDto.getAssemblies() ) ).thenReturn( true );

  // WHEN
  Optional<Website> actual = websiteService.addWebsite( websiteDto );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ) ) );
  verify( websiteDb, never() ).save( getPage( "A" ) );
 }

 @Test
 @DisplayName ("Update existing page should update the page in the db")
 void updateWebsite () {
  // GIVEN
  WebsiteDto websiteDto = WebsiteDto.builder()
                                  .slug( "A" )
                                  .userId( "B" )
                                  .assemblies( List.of() )
                                  .build();
  when( websiteDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ) ) );
  when( websiteDb.save( getPage( "A" ) ) ).thenReturn( getPage( "A" ) );
  when( timeUTC.now() ).thenReturn( 1L );
  when( assemblyService.hasAvailableAssemblies( websiteDto.getAssemblies() ) ).thenReturn( true );

  // WHEN
  Optional<Website> actual = websiteService.updateWebsite( websiteDto, "A" );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ) ) );
  verify( websiteDb ).save( getPage( "A" ) );
 }

 @Test
 @DisplayName ("Update existing page should return optional empty if assemblies are invalid")
 void updateWebsiteWithInvalidAssembly () {
  // GIVEN
  WebsiteDto websiteDto = WebsiteDto.builder()
                                  .slug( "A" )
                                  .userId( "B" )
                                  .assemblies( List.of() )
                                  .build();
  when( websiteDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ) ) );
  when( assemblyService.hasAvailableAssemblies( websiteDto.getAssemblies() ) ).thenReturn( false );

  // WHEN
  Optional<Website> actual = websiteService.updateWebsite( websiteDto, "A" );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( websiteDb, never() ).save( getPage( "A" ) );
 }

 @Test
 @DisplayName ("Update page should return an optional.empty if page doesnt exist")
 void updateNotExistingWebsite () {
  // GIVEN
  WebsiteDto websiteDto = WebsiteDto.builder()
                                  .slug( "A" )
                                  .userId( "B" )
                                  .assemblies( List.of() )
                                  .build();
  when( websiteDb.findById( "A" ) ).thenReturn( Optional.empty() );
  when( websiteDb.save( getPage( "A" ) ) ).thenReturn( getPage( "A" ) );
  when( timeUTC.now() ).thenReturn( 1L );

  // WHEN
  Optional<Website> actual = websiteService.updateWebsite( websiteDto, "A" );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( websiteDb, never() ).save( getPage( "A" ) );
 }

 @Test
 @DisplayName ("Update slug of existing page should return page with new slug")
 void updateWebsitesSlug () {
  // GIVEN
  WebsiteDto websiteDto = WebsiteDto.builder()
                                  .slug( "NEWSLUG" )
                                  .userId( "B" )
                                  .assemblies( List.of() )
                                  .build();
  String originalSlug = "A";
  when( websiteDb.findById( originalSlug ) ).thenReturn( Optional.of( getPage( originalSlug ) ) );
  when( websiteDb.save( getPage( "NEWSLUG" ) ) ).thenReturn( getPage( "NEWSLUG" ) );
  when( assemblyService.hasAvailableAssemblies( websiteDto.getAssemblies() ) ).thenReturn( true );
  when( timeUTC.now() ).thenReturn( 1L );

  // WHEN
  Optional<Website> actual = websiteService.updateWebsite( websiteDto, originalSlug );

  // THEN
  assertThat( actual.get(), is( getPage( "NEWSLUG" ) ) );
  verify( websiteDb ).save( getPage( "NEWSLUG" ) );
  verify( websiteDb ).deleteById( "A" );
 }

 @Test
 @DisplayName ("Update slug of not existing page should return optional empty")
 void updateNotExistingWebsitesSlug () {
  // GIVEN
  WebsiteDto websiteDto = WebsiteDto.builder()
                                  .slug( "NOTEXISTING" )
                                  .userId( "B" )
                                  .assemblies( List.of() )
                                  .build();
  String originalSlug = "A";
  when( websiteDb.findById( originalSlug ) ).thenReturn( Optional.empty() );
  when( timeUTC.now() ).thenReturn( 1L );

  // WHEN
  Optional<Website> actual = websiteService.updateWebsite( websiteDto, originalSlug );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( websiteDb, never() ).save( getPage( "NOTEXISTING" ) );
 }

 @Test
 @DisplayName ("Set landing page should set landingPage to true, remove all other landingPages " +
                       "and return it as an optional")
 void setLandingWebsiteBySlug () {
  // GIVEN
  when( websiteDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ) ) );
  when( websiteDb.findByLandingPageIs( true ) )
          .thenReturn( List.of( getPage( "B" ).toBuilder().landingPage( true ).build() ) );
  when( websiteDb.save( getPage( "A" ).toBuilder().landingPage( true ).build() ) ).then( returnsFirstArg() );
  when( websiteDb.save( getPage( "B" ).toBuilder().landingPage( false ).build() ) ).then( returnsFirstArg() );

  // WHEN
  Optional<Website> actual = websiteService.setLandingPageBySlug( "A" );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  verify( websiteDb ).save( getPage( "A" ).toBuilder().landingPage( true ).build() );
 }

 @Test
 @DisplayName ("Set landing page as landing page again should return it as optional")
 void setLandingWebsiteAgainAsLandingPageBySlug () {
  // GIVEN
  when( websiteDb.findById( "A" ) ).thenReturn( Optional.of( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  when( websiteDb.findByLandingPageIs( true ) )
          .thenReturn( List.of( getPage( "B" ).toBuilder().landingPage( false ).build() ) );
  when( websiteDb.save( getPage( "A" ).toBuilder().landingPage( true ).build() ) ).then( returnsFirstArg() );

  // WHEN
  Optional<Website> actual = websiteService.setLandingPageBySlug( "A" );

  // THEN
  assertThat( actual.get(), is( getPage( "A" ).toBuilder().landingPage( true ).build() ) );
  verify( websiteDb ).save( getPage( "A" ).toBuilder().landingPage( true ).build() );
 }

 @Test
 @DisplayName ("Set a non existing landing page should return optional empty")
 void setNotExistingWebsiteAsLandingPageBySlug () {
  // GIVEN
  when( websiteDb.findById( "A" ) ).thenReturn( Optional.empty() );

  // WHEN
  Optional<Website> actual = websiteService.setLandingPageBySlug( "NOTEXISTING" );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( websiteDb, never() ).save( any() );
 }

 @Test
 @DisplayName ("Delete page should call deleteById on the db")
 void deleteWebsiteBySlug () {
  // GIVEN
  // WHEN
  websiteService.deleteWebsiteBySlug( "A" );

  // THEN
  verify( websiteDb ).deleteById( "A" );
 }
}