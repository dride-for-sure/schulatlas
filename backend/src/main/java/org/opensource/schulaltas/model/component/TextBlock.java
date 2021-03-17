package org.opensource.schulaltas.model.component;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
@EqualsAndHashCode (callSuper = true)
public class TextBlock extends Component {

 private String title;
 private String subtitle;
 private String text;
}
