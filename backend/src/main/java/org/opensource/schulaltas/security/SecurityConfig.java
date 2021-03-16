package org.opensource.schulaltas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

 private final SchoolUserDetailsService schoolUserDetailsService;
 private final JwtRequestFilter jwtRequestFilter;

 @Autowired
 public SecurityConfig (
         SchoolUserDetailsService schoolUserDetailsService,
         JwtRequestFilter jwtRequestFilter) {
  this.schoolUserDetailsService = schoolUserDetailsService;
  this.jwtRequestFilter = jwtRequestFilter;
 }

 @Override
 protected void configure (AuthenticationManagerBuilder auth) throws Exception {
  auth.userDetailsService( schoolUserDetailsService );
 }

 @Override
 protected void configure (HttpSecurity http) throws Exception {
  http.csrf().disable()
          .authorizeRequests()
          .mvcMatchers( HttpMethod.POST, "/api/school" ).authenticated()
          .mvcMatchers( HttpMethod.PUT, "/api/school/**" ).authenticated()
          .mvcMatchers( HttpMethod.DELETE, "/api/school/**" ).hasRole( "ADMIN" )
          .mvcMatchers( HttpMethod.POST, "/api/page/**" ).authenticated()
          .mvcMatchers( HttpMethod.PUT, "/api/page/**" ).authenticated()
          .mvcMatchers( HttpMethod.DELETE, "/api/page/**" ).hasRole( "ADMIN" )
          .mvcMatchers( HttpMethod.PUT, "/api/landing-page/**" ).authenticated()
          .mvcMatchers( HttpMethod.DELETE, "/api/landing-page/**" ).hasRole( "ADMIN" )
          .mvcMatchers( HttpMethod.POST, "/api/property/**" ).authenticated()
          .mvcMatchers( HttpMethod.PUT, "/api/property/**" ).authenticated()
          .mvcMatchers( HttpMethod.DELETE, "/api/property/**" ).hasRole( "ADMIN" )
          .mvcMatchers( "/authenticate" ).permitAll()
          .and()
          .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS )
          .and()
          .addFilterBefore( jwtRequestFilter, UsernamePasswordAuthenticationFilter.class );
 }

 @Bean
 public PasswordEncoder getPasswordEncoder () {
  return new BCryptPasswordEncoder();
 }

 @Override
 @Bean
 public AuthenticationManager authenticationManagerBean () throws Exception {
  return super.authenticationManagerBean();
 }
}
