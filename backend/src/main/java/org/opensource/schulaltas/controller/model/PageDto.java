package org.opensource.schulaltas.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.opensource.schulaltas.model.page.HeroSecondary;
import org.opensource.schulaltas.model.page.Image;
import org.opensource.schulaltas.model.page.TextBlock;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {

 @Id
 private String name;
 private String author;
 private HeroSecondary hero;
 private TextBlock textBlock1;
 private Image image;
 private TextBlock textBlock2;
}
