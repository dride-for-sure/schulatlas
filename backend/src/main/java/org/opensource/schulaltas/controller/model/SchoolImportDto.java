package org.opensource.schulaltas.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolImportDto {
 private String address;
 private String official_id;
 private String lat;
 private String lon;
 private String name;
 private String phone;
 private String school_type;
}
