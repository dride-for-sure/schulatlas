package org.opensource.schulaltas.service;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TimeInstant {

 public Instant now () {
  return Instant.now();
 }
}
