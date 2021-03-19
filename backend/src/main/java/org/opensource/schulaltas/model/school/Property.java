package org.opensource.schulaltas.model.school;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
public class Property {

 private String name;
 private String unit;
 private String value;
}
