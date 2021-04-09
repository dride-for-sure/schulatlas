package org.opensource.schulaltas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ReactController {

 @RequestMapping (value = { "/", "/*", "/cms/**" })
 public String forward (HttpServletRequest httpServletRequest) {
  return "forward:/";
 }
}
