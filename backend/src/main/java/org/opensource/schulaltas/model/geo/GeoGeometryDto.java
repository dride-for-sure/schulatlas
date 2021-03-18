package org.opensource.schulaltas.model.geo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
@JsonIgnoreProperties (ignoreUnknown = true)
public class GeoGeometryDto {

 @JsonProperty ("location")
 private GeoLocationDto geocodeLocation;
}
