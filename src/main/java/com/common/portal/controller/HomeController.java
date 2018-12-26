package com.common.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

	@GetMapping({"/","/home"})
	public String home() {
		return "home";
	}

//	@GetMapping("/test")
//	public ModelAndView test(ModelAndView modelAndView) {
//		modelAndView.setViewName("test");
//		return modelAndView;
//	}
	@GetMapping("/test")
	public String test() {
		return "test";
	}

	@GetMapping("/dashboard")
	public ModelAndView dashboard(ModelAndView modelAndView) {
		modelAndView.setViewName("dashboard");
		return modelAndView;
	}

}
