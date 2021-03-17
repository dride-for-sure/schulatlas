package org.opensource.schulaltas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SchulaltasApplication {

 public static void main (String[] args) {
  SpringApplication.run( SchulaltasApplication.class, args );
 }

}
