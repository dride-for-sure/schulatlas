package org.opensource.schulaltas.service;

import lombok.extern.slf4j.Slf4j;
import org.opensource.schulaltas.config.GoogleGeoConfig;
import org.opensource.schulaltas.model.geo.GeoLocationDto;
import org.opensource.schulaltas.model.geo.GeoResultsDto;
import org.opensource.schulaltas.model.school.Address;
import org.opensource.schulaltas.model.school.Coordinates;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class GeoService {

 private final GoogleGeoConfig googleGeoConfig;
 private final RestTemplate restTemplate;

 public GeoService (GoogleGeoConfig googleGeoConfig, RestTemplate restTemplate) {
  this.googleGeoConfig = googleGeoConfig;
  this.restTemplate = restTemplate;
 }

 public Optional<Coordinates> getCoordinatesFromAddress (Address address) {
  Optional<GeoResultsDto> geoResultsDto = convertAddressToCoordinates( address );
  if ( geoResultsDto.isPresent() ) {
   GeoLocationDto geoLocationDto = geoResultsDto.get().getResults().get( 0 ).getGeometry().getGeocodeLocation();
   Coordinates coordinates = Coordinates.builder()
                                     .latitude( geoLocationDto.getLatitude() )
                                     .longitude( geoLocationDto.getLongitude() )
                                     .build();
   return Optional.of( coordinates );
  }
  return Optional.empty();
 }

 private Optional<GeoResultsDto> convertAddressToCoordinates (Address address) {
  String key = googleGeoConfig.getKey();
  String street = address.getStreet();
  String number = address.getNumber();
  String postcode = address.getPostcode();
  String city = address.getCity();
  String country = "DE";
  String base_url = "https://maps.googleapis.com/maps/api/geocode/json?address=";
  String extension = street + "+" + number + ",+" + postcode + "+" + city + ",+" + country + "&key=" + key;
  try {
   return Optional.of( restTemplate.getForEntity( base_url + extension, GeoResultsDto.class ).getBody() );
  } catch ( RestClientException e ) {
   log.warn( e.getMessage() );
   return Optional.empty();
  }
 }
}
