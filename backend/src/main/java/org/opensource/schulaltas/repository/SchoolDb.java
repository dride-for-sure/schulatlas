package org.opensource.schulaltas.repository;

import org.opensource.schulaltas.model.school.School;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolDb extends PagingAndSortingRepository<School, String> {

 @Override
 List<School> findAll ();
}
