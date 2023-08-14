package com.college.management.collegeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	@RequestMapping("/login")
	public String getLoginPage(@RequestParam(required = false) String error,Model model,@RequestParam(required = false) String logout) {
		String errorMessage="";
		if(error!=null) {
			errorMessage="Provide Valid Credintails";
		}
		if(logout!=null) {
			errorMessage="You have logged out successfully";
		}
		model.addAttribute("errorMessage",errorMessage);
		return "login";
	}

}
