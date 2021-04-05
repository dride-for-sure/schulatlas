package org.opensource.schulaltas.repository;

import org.opensource.schulaltas.model.website.Website;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebsiteDb extends PagingAndSortingRepository<Website, String> {

 @Override
 List<Website> findAll ();

 List<Website> findByLandingPageIs (boolean landingPage);
}
