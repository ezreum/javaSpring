package org.pap.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.pap.domain.Country;
import org.pap.exceptions.DangerException;
import org.pap.exceptions.InfoException;
import org.pap.helper.H;
import org.pap.helper.PRG;
import org.pap.helper.RolHelper;
import org.pap.repositories.RepositoryCountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/country")
public class CountryController {
	
	@Autowired
	private RepositoryCountry repoCountry;
	
	@GetMapping("")
	public String read(ModelMap data) {
		List<Country>countries = repoCountry.findAll();
		data.put("countries", countries);
		
		data.put("view", "/view/country/read");
		return "_t/frame";
	}
	
	@GetMapping("/create")
	public String createGet(ModelMap m, HttpSession s) {
		try {
			RolHelper.isRolOK("auth", s);
			s.getAttribute("person");
			m.put("view", "/view/country/create");
		} catch (Exception e) {
			m.put("view", "/view/anonymous/home");
		}
		
		return"/_t/frame";
	}
	
	@PostMapping("/create")
	public void createPost(
			@RequestParam("name")String name, HttpSession s) throws DangerException, InfoException {
		try {
			repoCountry.save(new Country(name));
		} catch (Exception e) {
			PRG.error(name+" is duplicated", "/country/create");
		}
		PRG.info(name+" has been sucessfully created", "/country");
	}
	
	@GetMapping("/update")
	public String update(
			@RequestParam("id")Long id,
			ModelMap m,
			HttpSession s) throws DangerException {
		RolHelper.isRolOK("auth", s);
		Country country = repoCountry.getOne(id);
		m.put("country", country);
		
		m.put("view", "/view/country/update");
		return "_t/frame";
	}
	
	@PostMapping("/update")
	public void update(
			@RequestParam("name")String name,
			@RequestParam("id")Long id,
			ModelMap m,
			HttpSession s) throws DangerException, InfoException {
			Country country = repoCountry.getOne(id);	
			try {
				country.setName(name);
				repoCountry.save(country);
			
			} catch (Exception e) {
			PRG.error("The selected country "+name+" hasn't been sucessfully updated", "/country");	
			}
			PRG.info("country "+name+" has been updated", "/country");
	}
	
	
	@PostMapping("/delete")
	public void Delete(@RequestParam("id")Long identifier, HttpSession s) throws DangerException, InfoException {
		RolHelper.isRolOK("auth", s);
		Country country = repoCountry.getOne(identifier);
		String name = country.getName();
		try {
			if (identifier!=null) {
		repoCountry.delete(country);
		}
		} catch (Exception e) {
			PRG.error(name+" has not been deleted", "/country/create");
		}
		PRG.info("Country "+name+" deleted correctly", "/country");
	}

}

	
