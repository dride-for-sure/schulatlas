package org.opensource.schulaltas.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class JwtConfig {

 @Value ("${security.jwt.secret}")
 private final String jwtSecret = "top-secret";
}

