package org.opensource.schulaltas.model.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder (toBuilder = true)
public class HeroPrimary {

 private String title;
 private String subtitle;
 private Button ctaLeft;
 private Button ctaRight;
 private Image image;
}
