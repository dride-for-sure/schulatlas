package org.opensource.schulaltas.model.attachment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opensource.schulaltas.model.attachment.enums.AttachmentType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document (collection = "attachments")
public class Attachment {

 @Id
 private String id;
 private String url;
 private AttachmentType type;
}
