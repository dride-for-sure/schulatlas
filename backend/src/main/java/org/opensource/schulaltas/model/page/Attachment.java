package org.opensource.schulaltas.model.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opensource.schulaltas.model.page.enums.AttachmentType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
@Document (collection = "attachments")
public class Attachment {

 @Id
 private String fileName;
 private String url;
 private AttachmentType type;
}
