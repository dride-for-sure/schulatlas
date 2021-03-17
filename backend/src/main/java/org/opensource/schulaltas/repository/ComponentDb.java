package org.opensource.schulaltas.repository;

import org.opensource.schulaltas.model.component.Component;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ComponentDb extends PagingAndSortingRepository<Component, String> {
}
