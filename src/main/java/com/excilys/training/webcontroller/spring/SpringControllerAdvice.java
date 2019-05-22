package com.excilys.training.webcontroller.spring;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class SpringControllerAdvice {
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String handle(Exception exp) {
		return "404";
	}
	
	

}
