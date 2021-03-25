package org.opensource.schulaltas.model.page.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder (toBuilder = true)
@EqualsAndHashCode (callSuper = true)
public class SubComponent extends Component {

 private List<Component> subcomponents;
}
