package com.college.management.collegeproject.exceptionhandlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionalHandler {
	@ExceptionHandler(Exception.class)
	public String exceptionalHandler(Exception ex) {
		return "error";
	}

}
