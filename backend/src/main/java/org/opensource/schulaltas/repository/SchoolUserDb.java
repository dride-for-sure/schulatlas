package org.opensource.schulaltas.repository;

import org.opensource.schulaltas.security.model.SchoolUser;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SchoolUserDb extends PagingAndSortingRepository<SchoolUser, String> {

 @Override
 List<SchoolUser> findAll ();
}