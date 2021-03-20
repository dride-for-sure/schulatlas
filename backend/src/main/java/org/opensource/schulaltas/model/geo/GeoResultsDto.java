package org.opensource.schulaltas.model.geo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
public class GeoResultsDto {

 private List<GeoObjectDto> results;
 private String status;
}
