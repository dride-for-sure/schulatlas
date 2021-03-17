package org.opensource.schulaltas.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opensource.schulaltas.model.component.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {

 private String name;

 @JsonProperty ("author")
 private String userId;
 private List<Component> components;
}
