package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TimeUTCTest {

 private final TimeUTC timeUTC = new TimeUTC();

 @Test
 @DisplayName ("Now should return unix timestamp for now")
 void now () {
  // WHEN
  Long actual = timeUTC.now();

  // THEN
  assertThat( actual, is( System.currentTimeMillis() ) );
 }
}