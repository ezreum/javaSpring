
package org.pap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HolaController {
	
	@GetMapping("/hola")
	public String holaGet(ModelMap data) {
		data.put("view", "view/hola/hola");
		return "_t/frame";
	}

	@PostMapping("/hola")
	//recogemos parametro de formulario
	public String holaPost(
			@RequestParam("name")String nombre,
			@RequestParam("surname")String surname, 
			ModelMap data) {
		data.put("name", nombre);
		data.put("surname", surname);
		
		data.put("view", "view/hola/holaPost");
		return "_t/frame";
	}
	
	
}
