package org.opensource.schulaltas.model.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
public class Component {
 private String type;
 private String variant;
 private String target;
 private String description;
 private String url;
 private String content;
 private List<Component> components;
}
