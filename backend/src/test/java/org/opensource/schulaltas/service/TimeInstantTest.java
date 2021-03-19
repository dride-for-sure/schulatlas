package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TimeInstantTest {

 private final TimeInstant timeInstant = new TimeInstant();

 @Test
 @DisplayName ("Now should return instant for now")
 void now () {
  // WHEN
  Instant actual = timeInstant.now();

  // THEN
  assertThat( actual, is( Instant.now() ) );
 }
}