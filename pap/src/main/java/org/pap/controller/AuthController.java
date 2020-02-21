package org.pap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

@Controller
public class AuthController {

	public String addProductToTrolley(ModelMap m) {
		
		m.put("view","/view/auth/addTrolley");
		return "/_t/frame";
	}
	
}
