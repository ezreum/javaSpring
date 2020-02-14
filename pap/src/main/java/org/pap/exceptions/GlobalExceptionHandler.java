package org.pap.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{

	@ExceptionHandler(RubenException.class)
	public String catapluf() {
		
		return "_error/HandlerDemo";
	}
	
}
