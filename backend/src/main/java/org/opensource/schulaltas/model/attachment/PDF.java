package org.opensource.schulaltas.model.attachment;

import lombok.*;
import org.opensource.schulaltas.model.attachment.enums.AttachmentType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
@EqualsAndHashCode (callSuper = true)
public class PDF extends Attachment {

 private final AttachmentType type = AttachmentType.PDF;
 private String description;

}
