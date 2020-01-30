package org.pap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HolaController {
	
	@GetMapping("/hola")
	public String holaGet() {
		return "view/hola/hola";
	}

	@PostMapping("/hola")
	public String holaPost() {
		
		return "view/hola/holaPost";
	}
	
	
}
