package org.opensource.schulaltas.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

 private final JwtConfig jwtConfig;
 private final TimeUtils timeUtils;
 private final SchoolUserDetailsService schoolUserDetailsService;

 public JwtRequestFilter (
         JwtConfig jwtConfig,
         TimeUtils timeUtils,
         SchoolUserDetailsService schoolUserDetailsService) {
  this.jwtConfig = jwtConfig;
  this.timeUtils = timeUtils;
  this.schoolUserDetailsService = schoolUserDetailsService;
 }

 @Override
 protected void doFilterInternal (
         HttpServletRequest request,
         HttpServletResponse response,
         FilterChain filterChain) throws ServletException, IOException {
  try {
   String authorizationHeader = request.getHeader( "Authorization" );
   if ( authorizationHeader != null ) {
    Jws<Claims> parsedToken = parseToken( authorizationHeader );
    if ( parsedToken.getBody().getExpiration().after( Date.from( timeUtils.now() ) ) ) {
     setSecurityContext( parsedToken );
    }
   }
  } catch ( Exception e ) {
   logger.warn( "failed to parse token", e );
  }
  filterChain.doFilter( request, response );
 }

 private Jws<Claims> parseToken (String authorizationHeader) {
  String token = authorizationHeader.replace( "Bearer:", "" ).trim();
  return Jwts.parser().setSigningKey( jwtConfig.getJwtSecret() ).parseClaimsJws( token );
 }


 private void setSecurityContext (Jws<Claims> parsedToken) {
  Claims body = parsedToken.getBody();
  UserDetails userDetails = schoolUserDetailsService.loadUserByUsername( body.getSubject() );
  UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken( userDetails, null, userDetails.getAuthorities() );
  SecurityContextHolder.getContext().setAuthentication( authentication );
 }
}

