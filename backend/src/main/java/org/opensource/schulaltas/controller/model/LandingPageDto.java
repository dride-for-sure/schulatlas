package org.opensource.schulaltas.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opensource.schulaltas.model.page.CardImage;
import org.opensource.schulaltas.model.page.HeroPrimary;
import org.opensource.schulaltas.model.page.Image;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LandingPageDto {

 @Id
 private String name;
 private String author;
 private HeroPrimary heroPrimary;

 @JsonProperty ("sponsors")
 private List<Image> sponsorList;
 private CardImage cardImageLeft;
 private CardImage cardImageRight;
}
