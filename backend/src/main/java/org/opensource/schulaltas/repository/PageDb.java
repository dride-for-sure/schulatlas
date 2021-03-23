package org.opensource.schulaltas.repository;

import org.opensource.schulaltas.model.page.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageDb extends PagingAndSortingRepository<Page, String> {

 @Override
 List<Page> findAll ();

 List<Page> findByLandingPageIs (boolean landingPage);
}
