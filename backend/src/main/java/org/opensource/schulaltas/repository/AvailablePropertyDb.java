package org.opensource.schulaltas.repository;

import org.opensource.schulaltas.model.school.AvailableProperty;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AvailablePropertyDb extends PagingAndSortingRepository<AvailableProperty, String> {

 @Override
 List<AvailableProperty> findAll ();
}
