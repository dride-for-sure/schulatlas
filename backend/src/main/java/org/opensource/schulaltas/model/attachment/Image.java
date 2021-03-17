package org.opensource.schulaltas.model.attachment;

import lombok.*;
import org.opensource.schulaltas.model.attachment.enums.AttachmentType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
@EqualsAndHashCode (callSuper = true)
public class Image extends Attachment {

 private final AttachmentType type = AttachmentType.IMAGE;
 private String description;
}
