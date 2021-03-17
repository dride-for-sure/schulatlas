package org.opensource.schulaltas.repository;

import org.opensource.schulaltas.model.attachment.Attachment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentDb extends PagingAndSortingRepository<Attachment, String> {

 @Override
 List<Attachment> findAll ();
}
