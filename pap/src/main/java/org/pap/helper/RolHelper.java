package org.pap.helper;

import javax.servlet.http.HttpSession;

import org.pap.domain.Person;

public class RolHelper {

	public static void isRolOK(String rol, HttpSession s){
		
		Person person = (Person)s.getAttribute("person");
			
			if (person == null) {
				if (rol!="anonymous") {
					
				}
			}
			else if (person != null) {
				if ( !person.getNick().equals("admin") && rol.equals("admin") ) {
					
				}
			else {
				System.out.println("aa");
				
			}
			}
	}
}
