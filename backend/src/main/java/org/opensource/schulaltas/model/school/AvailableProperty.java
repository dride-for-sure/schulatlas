package org.opensource.schulaltas.model.school;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
@Document (collection = "AvailableProperties")
public class AvailableProperty {

 @Id
 private String name;
 private String unit;
}
