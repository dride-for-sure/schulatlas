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
}
