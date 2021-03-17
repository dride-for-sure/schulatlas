package org.opensource.schulaltas.repository;

import org.opensource.schulaltas.model.property.Property;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PropertyDb extends PagingAndSortingRepository<Property, String> {

 @Override
 List<Property> findAll ();
}
