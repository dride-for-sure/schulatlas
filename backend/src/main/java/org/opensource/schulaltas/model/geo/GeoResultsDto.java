package org.opensource.schulaltas.model.geo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
@JsonIgnoreProperties (ignoreUnknown = true)
public class GeoResultsDto {

 private List<GeoObjectDto> results;
 private String status;
}
