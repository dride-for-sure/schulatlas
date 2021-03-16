package org.opensource.schulaltas.repository;

import org.opensource.schulaltas.model.page.LandingPage;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandingPageDb extends PagingAndSortingRepository<LandingPage, String> {
}
