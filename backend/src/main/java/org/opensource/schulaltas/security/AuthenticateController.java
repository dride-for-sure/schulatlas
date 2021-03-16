package org.opensource.schulaltas.security;

import org.opensource.schulaltas.security.model.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping ("/authenticate")
public class AuthenticateController {

 private final AuthenticationManager authenticationManager;
 private final JwtUtils jwtUtils;

 @Autowired
 public AuthenticateController (AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
  this.authenticationManager = authenticationManager;
  this.jwtUtils = jwtUtils;
 }

 @PostMapping
 public String createAuthenticationToken (@RequestBody AuthenticationRequest authenticationRequest) {
  try {
   authenticationManager.authenticate(
           new UsernamePasswordAuthenticationToken(
                   authenticationRequest.getUsername(), authenticationRequest.getPassword() ) );
  } catch ( Exception e ) {
   throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Credentials are invalid" );
  }
  return jwtUtils.createToken( authenticationRequest.getUsername() );
 }
}
