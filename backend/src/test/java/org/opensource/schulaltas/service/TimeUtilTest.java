package org.opensource.schulaltas.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class TimeUtilTest {

 private final TimeUtil timeUtil = new TimeUtil();

 @Test
 @DisplayName ("Now should return unix timestamp for now")
 void now () {
  // GIVEN
  // WHEN
  Long actual = timeUtil.now();

  // THEN
  assertThat( actual, is( System.currentTimeMillis() ) );
 }
}