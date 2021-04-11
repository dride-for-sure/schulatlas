package org.opensource.schulaltas.repository;

import org.opensource.schulaltas.model.school.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolDb extends PagingAndSortingRepository<School, String> {

 @Override
 List<School> findAll ();

 @Override
 Page<School> findAll (Pageable pageable);

 Page<School> findAllByType (String type, Pageable pageable);

 Page<School> findAllByTypeContainingIgnoreCaseOrNameContainingIgnoreCaseOrAddress_CityContainingIgnoreCaseOrAddress_StreetContainingIgnoreCaseOrAddress_PostcodeContainingIgnoreCaseOrNumberContainingIgnoreCase (
         String type, String name, String address_city, String address_street,
         String address_postcode, String number, Pageable pageable);

 List<School> findAllByCoordinates_LatitudeBetweenAndCoordinates_LongitudeBetween (Double coordinates_latitude, Double coordinates_latitude2, Double coordinates_longitude, Double coordinates_longitude2);

 List<School> findAllByTypeAndCoordinates_LatitudeBetweenAndCoordinates_LongitudeBetween (String type, Double coordinates_latitude, Double coordinates_latitude2, Double coordinates_longitude, Double coordinates_longitude2);

}