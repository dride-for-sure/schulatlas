package org.opensource.schulaltas.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties (prefix = "spring.data.mongodb")
public class MongoDbConfig {

 private String host;
 private int port;
}
