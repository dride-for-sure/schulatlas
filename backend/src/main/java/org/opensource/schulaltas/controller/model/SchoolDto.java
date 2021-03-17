package org.opensource.schulaltas.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opensource.schulaltas.model.school.Address;
import org.opensource.schulaltas.model.school.Contact;
import org.opensource.schulaltas.model.school.Property;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDto {

 private String number;
 private String name;
 private Address address;
 private Contact contact;

 @JsonProperty ("author")
 private String userId;
 private List<Property> properties;
}
