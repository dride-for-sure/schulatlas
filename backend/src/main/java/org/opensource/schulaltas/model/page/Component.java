package org.opensource.schulaltas.model.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
@Document (collection = "components")
public class Component {

 @Id
 private String type;
 private Map<String, List<Object>> components;
}
