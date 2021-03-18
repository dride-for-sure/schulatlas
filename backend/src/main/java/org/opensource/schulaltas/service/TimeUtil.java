package org.opensource.schulaltas.service;

import org.springframework.stereotype.Service;

@Service
public class TimeUtil {

 public Long now () {
  return System.currentTimeMillis();
 }
}
