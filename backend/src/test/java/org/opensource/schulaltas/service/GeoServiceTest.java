package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.config.GoogleGeoConfig;
import org.opensource.schulaltas.model.geo.GeoGeometryDto;
import org.opensource.schulaltas.model.geo.GeoLocationDto;
import org.opensource.schulaltas.model.geo.GeoObjectDto;
import org.opensource.schulaltas.model.geo.GeoResultsDto;
import org.opensource.schulaltas.model.school.Address;
import org.opensource.schulaltas.model.school.Coordinates;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class GeoServiceTest {

 private final GoogleGeoConfig googleGeoConfig = mock( GoogleGeoConfig.class );
 private final RestTemplate restTemplate = mock( RestTemplate.class );
 private final GeoService geoService = new GeoService( googleGeoConfig, restTemplate );

 @Test
 @DisplayName ("Get coordinates should return the GeoResultsDto")
 void getCoordinatesFromAddress () {
  // GIVEN
  Address address = Address.builder()
                            .city( "A" )
                            .number( "1" )
                            .street( "B" )
                            .postcode( "1" )
                            .build();
  String key = "key";
  String street = address.getStreet();
  String number = address.getNumber();
  String postcode = address.getPostcode();
  String city = address.getCity();
  String country = "DE";
  String base_url = "https://maps.googleapis.com/maps/api/geocode/json?address=";
  String extension = street + "+" + number + ",+" + postcode + "+" + city + ",+" + country + "&key=" + key;

  GeoLocationDto geoLocationDto = GeoLocationDto.builder()
                                          .latitude( 1.0 )
                                          .longitude( 2.0 )
                                          .build();
  GeoGeometryDto geoGeometryDto = GeoGeometryDto.builder()
                                          .geocodeLocation( geoLocationDto )
                                          .build();
  GeoObjectDto geoObjectDto = GeoObjectDto.builder()
                                      .geometry( geoGeometryDto ).build();
  GeoResultsDto geoResultsDto = GeoResultsDto.builder()
                                        .status( "OK" )
                                        .results( List.of( geoObjectDto ) ).build();

  when( googleGeoConfig.getKey() ).thenReturn( key );
  when( restTemplate.getForEntity( base_url + extension, GeoResultsDto.class ) )
          .thenReturn( ResponseEntity.ok( geoResultsDto ) );

  // WHEN
  Optional<Coordinates> actual = geoService.getCoordinatesFromAddress( address );

  // THEN
  Coordinates expected = Coordinates.builder()
                                 .latitude( 1.0 )
                                 .longitude( 2.0 )
                                 .build();
  assertThat( actual.get(), is( expected ) );
  verify( restTemplate ).getForEntity( base_url + extension, GeoResultsDto.class );
 }

 @Test
 @DisplayName ("Get coordinates should return optional.empty if google geo api throws exception")
 void getCoordinatesFromInvalidAddress () {
  // GIVEN
  Address address = Address.builder()
                            .city( "A" )
                            .number( "1" )
                            .street( "B" )
                            .postcode( "1" )
                            .build();
  String key = "key";
  String street = address.getStreet();
  String number = address.getNumber();
  String postcode = address.getPostcode();
  String city = address.getCity();
  String country = "DE";
  String base_url = "https://maps.googleapis.com/maps/api/geocode/json?address=";
  String extension = street + "+" + number + ",+" + postcode + "+" + city + ",+" + country + "&key=" + key;

  when( googleGeoConfig.getKey() ).thenReturn( key );
  when( restTemplate.getForEntity( base_url + extension, GeoResultsDto.class ) )
          .thenThrow( RestClientException.class );

  // WHEN
  Optional<Coordinates> actual = geoService.getCoordinatesFromAddress( address );

  // THEN
  assertThat( actual.isEmpty(), is( true ) );
  verify( restTemplate ).getForEntity( base_url + extension, GeoResultsDto.class );
 }
}