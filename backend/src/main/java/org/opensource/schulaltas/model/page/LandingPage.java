package org.opensource.schulaltas.model.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
@Document (collection = "landingPage")
public class LandingPage {

 @Id
 private String name;
 private Date updated;
 private String author;
 private HeroPrimary heroPrimary;

 @JsonProperty ("sponsors")
 private List<Image> sponsorList;
 private CardImage cardImageLeft;
 private CardImage cardImageRight;

}
