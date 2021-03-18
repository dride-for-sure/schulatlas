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
public class GeoAddressDto {

 @JsonProperty ("long_name")
 private String longName;

 @JsonProperty ("short_name")
 private String shortName;
 List<String> types;
}
