package org.opensource.schulaltas.model.school;

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
@Document (collection = "schools")
public class School {

 @Id
 private String number;
 private String name;
 private Address address;
 private Contact contact;
 private Coordinates coordinates;
 private Long updated;
 private String userId;
 private Integer markedOutdated;
 List<Property> properties;
}
