package org.opensource.schulaltas.service;

import org.opensource.schulaltas.model.school.School;
import org.opensource.schulaltas.repository.SchoolDb;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MapService {

 private final SchoolDb schoolDb;

 public MapService (SchoolDb schoolDb) {
  this.schoolDb = schoolDb;
 }

 public List<School> searchMapByBounds (String northEast, String southWest) {
  Double[] northEastCoordinates = getDoubles( northEast );
  Double[] southWestCoordinates = getDoubles( southWest );
  List<School> schools = schoolDb.findAllByCoordinates_LatitudeBetweenAndCoordinates_LongitudeBetween(
          southWestCoordinates[ 0 ],
          northEastCoordinates[ 0 ],
          southWestCoordinates[ 1 ],
          northEastCoordinates[ 1 ]
  );
  return schools;
 }

 public List<School> searchMapByBoundsAndType (String type, String northEast, String southWest) {
  Double[] northEastCoordinates = getDoubles( northEast );
  Double[] southWestCoordinates = getDoubles( southWest );
  List<School> schools =
          schoolDb.findAllByTypeAndCoordinates_LatitudeBetweenAndCoordinates_LongitudeBetween(
                  type,
                  southWestCoordinates[ 0 ],
                  northEastCoordinates[ 0 ],
                  southWestCoordinates[ 1 ],
                  northEastCoordinates[ 1 ]
          );
  return schools;
 }

 public List<School> searchMapByStringAndNearest (String search, String northEast, String southWest) {
  Double[] northEastCoordinates = getDoubles( northEast );
  Double[] southWestCoordinates = getDoubles( southWest );
  Double[] centerCoordinates = new Double[ 2 ];
  centerCoordinates[ 0 ] = northEastCoordinates[ 0 ] - southWestCoordinates[ 0 ];
  centerCoordinates[ 1 ] = northEastCoordinates[ 1 ] - southWestCoordinates[ 1 ];

  List<School> schools = schoolDb.findAll();

  // TODO: SEARCH FOR NEAREST!
  return schools;

 }

 private Double[] getDoubles (String latlng) {
  return Arrays.stream( latlng.split( "," ) )
                 .map( Double::valueOf )
                 .toArray( Double[]::new );
 }
}
