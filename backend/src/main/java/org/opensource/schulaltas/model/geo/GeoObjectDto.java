package org.opensource.schulaltas.model.geo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class GeoObjectDto {

 @JsonProperty ("place_id")
 private String placeId;

 @JsonProperty ("address_components")
 private List<GeoAddressDto> addressComponents;

 @JsonProperty ("formatted_address")
 private String formattedAddress;
 private GeoGeometryDto geometry;
}
