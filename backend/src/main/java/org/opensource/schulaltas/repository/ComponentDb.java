package org.opensource.schulaltas.repository;

import org.opensource.schulaltas.model.page.Component;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ComponentDb extends PagingAndSortingRepository<Component, String> {

 @Override
 List<Component> findAll ();
}
