package org.opensource.schulaltas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactController {

 @RequestMapping (value = { "/" })
 public String index () {
  return "index.html";
 }

 @RequestMapping (value = { "/*" })
 public String schulatlas () {
  return "/";
 }

 @RequestMapping (value = { "/cms/**" })
 public String cms () {
  return "/";
 }
}
