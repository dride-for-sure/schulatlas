package org.opensource.schulaltas.model.school;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opensource.schulaltas.model.property.Property;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
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
 private Geo geo;
 private Date updated;
 private String user;
 private Integer markedOutdated;

 @JsonProperty ("properties")
 List<Property> properties;
}
