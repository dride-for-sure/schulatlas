package org.opensource.schulaltas.model.school;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
public class GeoObject {

 private final String type = "Point";
 private Coordinates coordinates;
}
