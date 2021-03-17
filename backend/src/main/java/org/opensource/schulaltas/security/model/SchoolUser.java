package org.opensource.schulaltas.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opensource.schulaltas.security.model.enums.SchoolUserRoles;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
@Document (collection = "users")
public class SchoolUser {

 @Id
 private String id;
 private String name;
 private String password;
 List<SchoolUserRoles> roles;
}
