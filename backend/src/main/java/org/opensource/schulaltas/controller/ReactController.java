package org.opensource.schulaltas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactController {

 @RequestMapping (value = { "/", "/*", "/cms/**" })
 public String forward () {
  return "index.html";
 }

 @RequestMapping (value = { "/*" })
 public String forward () {
  return "/";
 }

 @RequestMapping (value = { "/cms/**" })
 public String forward () {
  return "/";
 }
}
