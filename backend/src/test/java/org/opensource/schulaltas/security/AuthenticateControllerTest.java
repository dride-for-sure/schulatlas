package org.opensource.schulaltas.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opensource.schulaltas.repository.SchoolUserDb;
import org.opensource.schulaltas.security.model.AuthenticationRequest;
import org.opensource.schulaltas.security.model.SchoolUser;
import org.opensource.schulaltas.security.model.enums.SchoolUserAuthorities;
import org.opensource.schulaltas.service.TimeInstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource (properties = "security.jwt.secret=testsecret")
class AuthenticateControllerTest {

 @LocalServerPort
 private int port;

 @Autowired
 private TimeInstant timeInstant;

 @Autowired
 private SchoolUserDb schoolUserDb;

 @Autowired
 private PasswordEncoder passwordEncoder;

 @Autowired
 private TestRestTemplate testRestTemplate;

 @BeforeEach
 public void Setup () {
  String testUser = "testUser";
  String testPassword = "testPassword";
  String encodedPassword = passwordEncoder.encode( testPassword );
  schoolUserDb.save( SchoolUser.builder()
                             .username( testUser )
                             .password( encodedPassword )
                             .authorities( List.of( SchoolUserAuthorities.ADMIN ) )
                             .build() );
 }

 private String getUrl () {
  return "http://localhost:" + port + "authenticate";
 }

 @Test
 @DisplayName ("Create authentication token should generate a valid token valid credentials")
 void createAuthenticationToken () {
  // WHEN
  AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                                                        .username( "testUser" )
                                                        .password( "testPassword" )
                                                        .build();
  ResponseEntity<String> response = testRestTemplate.postForEntity( getUrl(), authenticationRequest, String.class );

  // THEN
  Instant nowInstant = Instant.ofEpochSecond( Instant.now().getEpochSecond() );
  Claims claims = Jwts.parser().setSigningKey( "testsecret" ).parseClaimsJws( response.getBody() ).getBody();
  assertThat( response.getStatusCode(), is( HttpStatus.OK ) );
  assertThat( claims.getSubject(), is( "testUser" ) );
  assertThat( claims.getExpiration(), is( Date.from( nowInstant.plus( Duration.ofHours( 12 ) ) ) ) );
 }

 @Test
 @DisplayName ("Create authentication token should throw an exception for invalid credentials")
 void createAuthenticationTokenThrowsException () {
  // WHEN
  AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                                                        .username( "anotherTestUser" )
                                                        .password( "anotherTestPassword" )
                                                        .build();
  ResponseEntity<String> response = testRestTemplate.postForEntity( getUrl(), authenticationRequest, String.class );

  // THEN
  assertThat( response.getStatusCode(), is( HttpStatus.BAD_REQUEST ) );
 }
}