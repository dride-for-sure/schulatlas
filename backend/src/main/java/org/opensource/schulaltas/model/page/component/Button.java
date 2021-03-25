package org.opensource.schulaltas.model.page.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder (toBuilder = true)
@EqualsAndHashCode (callSuper = true)
public class Button extends Component {

 private String target;
 private String description;
 private String content;
}
