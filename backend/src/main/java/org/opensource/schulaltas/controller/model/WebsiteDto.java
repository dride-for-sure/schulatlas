package org.opensource.schulaltas.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opensource.schulaltas.model.website.Assembly;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
public class WebsiteDto {

 private String slug;
 private String userId;
 private List<Assembly> assemblies;
}
