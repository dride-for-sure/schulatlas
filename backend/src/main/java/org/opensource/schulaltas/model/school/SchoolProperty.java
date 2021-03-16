package org.opensource.schulaltas.model.school;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opensource.schulaltas.model.enums.SchoolPropertyType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
public class SchoolProperty {

 private SchoolPropertyType type;
 private String value;
 private String unit;
}
