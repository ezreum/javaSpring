package org.pap.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

	@GetMapping("/logout")
	public String logout(HttpSession s) {
		s.invalidate();
		return "redirect:/";
	}
	
	
	public String addProductToTrolley(ModelMap m) {
		
		m.put("view","/view/auth/addTrolley");
		return "/_t/frame";
	}
	
	
	
}
