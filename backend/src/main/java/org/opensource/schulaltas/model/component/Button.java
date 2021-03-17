package org.opensource.schulaltas.model.component;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
@EqualsAndHashCode (callSuper = true)
public class Button extends Component {

 private String title;
 private String url;
}
