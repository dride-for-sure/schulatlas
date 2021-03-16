package org.opensource.schulaltas.repository;

import org.opensource.schulaltas.model.property.Property;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PropertiesDb extends PagingAndSortingRepository<Property, String> {
}
