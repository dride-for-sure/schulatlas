package org.opensource.schulaltas.model.school;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
@Document (collection = "schools")
public class School {

 @Id
 @Indexed
 private String number;
 @Indexed
 private String name;
 @Indexed
 private Address address;
 @Indexed
 private Contact contact;
 private String image;
 @Indexed
 private String type;
 private Coordinates coordinates;
 private Long updated;
 private String userId;
 private Integer markedOutdated;
 private List<Property> properties;
}
