package com.college.management.collegeproject.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class HomeController {
	@RequestMapping("/home")
	public String getHomePage(Model model, Authentication authentication) {
		model.addAttribute("authentication",authentication.getName());
		model.addAttribute("role",authentication.getAuthorities().toString());
		model.addAttribute("faculty");
		return "home";
	}

}
