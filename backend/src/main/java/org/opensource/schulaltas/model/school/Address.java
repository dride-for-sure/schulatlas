package org.opensource.schulaltas.model.school;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
public class Address {

 private String street;
 private String number;
 private String postcode;
 private String city;
}
