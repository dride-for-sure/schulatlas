package org.opensource.schulaltas.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtUtils {

 private final TimeUtils timeUtils;
 private final JwtConfig config;

 public JwtUtils (TimeUtils timeUtils, JwtConfig config) {
  this.timeUtils = timeUtils;
  this.config = config;
 }

 public String createToken (String subject) {
  Instant now = timeUtils.now();
  return Jwts.builder()
                 .setSubject( subject )
                 .setIssuedAt( Date.from( now ) )
                 .setExpiration( Date.from( now.plus( Duration.ofHours( 4 ) ) ) )
                 .signWith( SignatureAlgorithm.HS256, config.getJwtSecret() )
                 .compact();
 }
}
