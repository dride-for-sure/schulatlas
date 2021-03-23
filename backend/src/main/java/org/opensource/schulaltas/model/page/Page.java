package org.opensource.schulaltas.model.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
@Document (collection = "pages")
public class Page {

 @Id
 private String name;
 private Long updated;
 private String userId;
 private boolean landingPage;
 private List<Assembly> assemblies;

}
