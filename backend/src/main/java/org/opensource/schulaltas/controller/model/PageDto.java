package org.opensource.schulaltas.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opensource.schulaltas.model.page.Component;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
public class PageDto {

 private String name;
 private String userId;
 private List<Component> components;
}
