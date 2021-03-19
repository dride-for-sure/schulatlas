package org.opensource.schulaltas.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.opensource.schulaltas.service.TimeInstant;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtUtils {

 private final TimeInstant timeInstant;
 private final JwtConfig config;

 public JwtUtils (TimeInstant timeInstant, JwtConfig config) {
  this.timeInstant = timeInstant;
  this.config = config;
 }

 public String createToken (String subject) {
  Instant now = timeInstant.now();
  return Jwts.builder()
                 .setSubject( subject )
                 .setIssuedAt( Date.from( now ) )
                 .setExpiration( Date.from( now.plus( Duration.ofHours( 12 ) ) ) )
                 .signWith( SignatureAlgorithm.HS256, config.getJwtSecret() )
                 .compact();
 }
}
