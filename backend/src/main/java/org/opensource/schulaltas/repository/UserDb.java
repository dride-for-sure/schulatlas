package org.opensource.schulaltas.repository;

import org.opensource.schulaltas.security.model.SchoolUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDb extends PagingAndSortingRepository<SchoolUser, String> {
}
