package com.college.management.collegeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
	@GetMapping("/profile")
	public String getProfile() {
		return "profile";
	}

}
