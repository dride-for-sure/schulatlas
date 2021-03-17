package org.opensource.schulaltas.model.component;

import lombok.*;
import org.opensource.schulaltas.model.attachment.Image;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
@EqualsAndHashCode (callSuper = true)
public class CardImage extends Component {

 private String title;
 private String subtitle;
 private String text;
 private Image image;
}
