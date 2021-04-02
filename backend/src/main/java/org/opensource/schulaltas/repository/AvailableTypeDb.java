package org.opensource.schulaltas.repository;

import org.opensource.schulaltas.model.school.AvailableType;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AvailableTypeDb extends PagingAndSortingRepository<AvailableType, String> {
 @Override
 List<AvailableType> findAll ();
}
