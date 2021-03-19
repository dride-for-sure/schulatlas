package org.opensource.schulaltas.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.service.TimeInstant;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtUtilsTest {

 private final TimeInstant timeInstant = mock( TimeInstant.class );
 private final JwtConfig jwtConfig = mock( JwtConfig.class );
 private final JwtUtils jwtUtils = new JwtUtils( timeInstant, jwtConfig );

 @Test
 @DisplayName ("Create token should generate a valid jwt")
 void createToken () {
  // GIVEN
  Instant nowInstant = Instant.ofEpochSecond( Instant.now().getEpochSecond() );
  String testSecret = "secret";

  when( timeInstant.now() ).thenReturn( nowInstant );
  when( jwtConfig.getJwtSecret() ).thenReturn( testSecret );

  // THEN
  String jwtToken = jwtUtils.createToken( "testUser" );

  // THEN
  Claims claims = Jwts.parser().setSigningKey( testSecret ).parseClaimsJws( jwtToken ).getBody();
  assertThat( claims.getSubject(), is( "testUser" ) );
  assertThat( claims.getIssuedAt(), is( Date.from( nowInstant ) ) );
  assertThat( claims.getExpiration(), is( Date.from( nowInstant.plus( Duration.ofHours( 12 ) ) ) ) );
 }
}