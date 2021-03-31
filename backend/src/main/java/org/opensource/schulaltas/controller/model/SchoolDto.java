package org.opensource.schulaltas.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opensource.schulaltas.model.school.Address;
import org.opensource.schulaltas.model.school.Contact;
import org.opensource.schulaltas.model.school.Property;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
public class SchoolDto {

 private String number;
 private String name;
 private Address address;
 private Contact contact;
 private String image;
 private String type;
 private String userId;
 private List<Property> properties;
}
