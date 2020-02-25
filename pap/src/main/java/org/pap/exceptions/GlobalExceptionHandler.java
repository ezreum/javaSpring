package org.pap.exceptions;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class GlobalExceptionHandler{

	@ExceptionHandler(DangerException.class)
	public RedirectView danger(HttpSession s, DangerException e) {
		String text = e.getMessage() != null && e.getMessage() != "" ? e.getMessage().split("@")[0] : "there's been an error. Press the button to go home";
		String link = e.getMessage() != null && e.getMessage() != "" && e.getMessage().split("@").length >1 ? e.getMessage().split("@")[1] : "/";

		s.setAttribute("_text", text);
	    s.setAttribute("_link", link);
	    s.setAttribute("_severity", "danger");
	    
	    return new RedirectView("/info");
	}

	@ExceptionHandler(InfoException.class)
	public RedirectView info(HttpSession s, InfoException e) {
		String text = e.getMessage() != null && e.getMessage() != "" ? e.getMessage().split("@")[0] : "there's been an error. Press the button to go home";
		String link = e.getMessage() != null && e.getMessage() != "" && e.getMessage().split("@").length >1 ? e.getMessage().split("@")[1] : "/";

		s.setAttribute("_text", text);
	    s.setAttribute("_link", link);
	    s.setAttribute("_severity", "info");
	    
	    return new RedirectView("/info");
	}
	
	
}
