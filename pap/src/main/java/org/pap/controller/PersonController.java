package org.pap.controller;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Response;
import org.pap.domain.Country;
import org.pap.domain.Hobby;
import org.pap.domain.Person;
import org.pap.exceptions.DangerException;
import org.pap.exceptions.InfoException;
import org.pap.helper.H;
import org.pap.helper.PRG;
import org.pap.helper.RolHelper;
import org.pap.repositories.RepositoryCountry;
import org.pap.repositories.RepositoryHobby;
import org.pap.repositories.RepositoryPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/person")
public class PersonController {
	@Autowired
	private RepositoryPerson repoPerson;
	
	@Autowired
	private RepositoryCountry repoCountry;
	
	@Autowired
	private RepositoryHobby repoHobby;
	
	
	@GetMapping("")
	public String read(ModelMap m) {
		List<Person> people=repoPerson.findAll();
		m.put("people", people);
		
		m.put("view", "/view/person/read");
		return "/_t/frame";
	}
	
	@GetMapping("/create")
	public String create(ModelMap m, HttpSession s) throws DangerException {
		RolHelper.isRolOK("auth", s);
		List<Country> country = repoCountry.findAll();
		m.put("countries", country);
		List<Hobby> hobbies= repoHobby.findAll();
		m.put("hobbies", hobbies);
		
		m.put("view", "/view/person/create");
		return "_t/frame";
	}
	
	@PostMapping("/create")
	public void create(
			@RequestParam("name")String name,
			@RequestParam("nick")String nick,
			@RequestParam("password")String pwd,
			@RequestParam(value="birthdate", required=false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate birthdate,
			@RequestParam(value="born", required=false)Long born,
			@RequestParam(value = "likedHobbies[]", required=false)List<Long> likedHobbies,
			@RequestParam(value = "hatedHobbies[]", required=false)List<Long> hatedHobbies,
			HttpSession s,
			ModelMap m
			) throws DangerException, InfoException {
		String route="/person";
		
		
		try {
			
			Country country = repoCountry.getOne(born);
			Person person = new Person(name,nick,pwd,birthdate);
			country.getAreBorn().add(person);
			person.setBorn(country);
			likedHobbies = (likedHobbies == null?new ArrayList<Long>():likedHobbies);
			hatedHobbies = (hatedHobbies == null?new ArrayList<Long>():hatedHobbies);
			
			for (Long idHobby : likedHobbies) {
				Hobby hobby = repoHobby.getOne(idHobby);
				hobby.getPeopleWhoLike().add(person);
				person.getLikedThings().add(hobby);
			}
			
			for (Long idHobby : hatedHobbies) {
				Hobby hobby= repoHobby.getOne(idHobby);
				hobby.getPeopleWhoHate().add(person);
				person.getHatedThings().add(hobby);
			}
			
			repoPerson.save(person);
			
		} catch (Exception e) {
			PRG.error("person named "+name+" hasn't been properly created", route);
		}
		PRG.info("person named "+name+" has been successfully created", route);
	}
	
	
	@GetMapping("/update")
	public String update(
			@RequestParam("id")Long id,
			ModelMap m, HttpSession s) throws DangerException {
		RolHelper.isRolOK("auth", s);
		m.put("person", repoPerson.getOne(id));
		m.put("countries", repoCountry.findAll());
		m.put("hobbies", repoHobby.findAll());
		
		m.put("view", "/view/person/update");
		return "_t/frame";
	}
	
	@PostMapping("/update")
	public void updatePostBorr(
			@RequestParam("id")Long id,
			@RequestParam("name")String name,
			@RequestParam(value="birthdate", required=false)
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			LocalDate birthdate,
			@RequestParam("born")Long idCountry,
			@RequestParam(value="likedHobbies[]", required=false) List<Long> liked,
			@RequestParam(value="hatedHobbies[]", required=false) List<Long> hated,
			HttpSession s
			) throws DangerException, InfoException {
		try {
			liked = (liked == null ? new ArrayList<Long>() : liked);
			hated = (hated == null ? new ArrayList<Long>() : hated);
			
			Person person = repoPerson.getOne(id);
			person.setName(name);
			
			person.setBirthdate(birthdate);
			Country country = repoCountry.getOne(idCountry);
			
			//country.getAreBorn().remove(person);
			person.setBorn(null);
			
			country.getAreBorn().add(person);
			person.setBorn(country);
			
			/*
			 * for (Long long1: liked) { Hobby hobby = repoHobby.getOne(long1);
			 * hobby.getPeopleWhoLiked().remove(person);
			 * person.getLikedThings().remove(  person.setLikedThings(likedHobbies);hobby); }
			 */
			


			// Creo nueva colección de gustos nuevos y la sustituyo por la antigua
			
			 List<Hobby> likedHobbies = new ArrayList<Hobby>();
			 	for (Long idliked : liked) { 
			 		Hobby hobby = repoHobby.getOne(idliked);
			 	//	hobby.getPeopleWhoLiked().add(person);
			 		likedHobbies.add(hobby);
			}
			  person.setLikedThings(likedHobbies);
			 
			  List<Hobby> hatedHobbies = new ArrayList<Hobby>();
				for (Long long1 : hated) { 
					Hobby hobby = repoHobby.getOne(long1);
				//	hobby.getPeopleWhoHate().add(person);
					hatedHobbies.add(hobby);
				}
				 person.setHatedThings(hatedHobbies);
			
			repoPerson.save(person);
			
			
			
		} catch (Exception e) {
			PRG.error("El usuario "+name+" no se ha modificado correctamente", "/person");
		}
		PRG.info("El usuario "+name+" se ha modificado correctamente", "/person");
	}
	
	@PostMapping("/delete")
	public void delete(
			@RequestParam("id")Long id,
			HttpSession s
			) throws DangerException, InfoException {
		RolHelper.isRolOK("auth", s);
		Person person = repoPerson.getOne(id);
		String name = person.getName();
		try {
			repoPerson.delete(person);
		} catch (Exception e) {
			PRG.error("person named "+name+" has been sucessfully erased", "/person");
		}
		PRG.info("person named "+name+" has been sucessfully erased", "/person" );
	}
	
	
}
