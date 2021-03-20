package org.opensource.schulaltas.security;

import org.opensource.schulaltas.repository.SchoolUserDb;
import org.opensource.schulaltas.security.model.SchoolUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SchoolUserDetailsService implements UserDetailsService {

 private final SchoolUserDb schoolUserDb;

 @Autowired
 public SchoolUserDetailsService (SchoolUserDb schoolUserDb) {
  this.schoolUserDb = schoolUserDb;
 }

 @Override
 public UserDetails loadUserByUsername (String id) throws UsernameNotFoundException {
  SchoolUser user = schoolUserDb.findById( id )
                            .orElseThrow( () -> new UsernameNotFoundException( "User with id: " + id + " does not exists" ) );
  return User.builder()
                 .username( user.getUsername() )
                 .password( user.getPassword() )
                 .authorities( user.getAuthorities()
                                       .stream()
                                       .map( gameUserRoles -> new SimpleGrantedAuthority( gameUserRoles.toString() ) )
                                       .collect( Collectors.toList() ) )
                 .build();
 }
}
