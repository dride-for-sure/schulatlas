package org.opensource.schulaltas.repository;

import org.opensource.schulaltas.model.website.Assembly;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AssemblyDb extends PagingAndSortingRepository<Assembly, String> {

 @Override
 List<Assembly> findAll ();
}
