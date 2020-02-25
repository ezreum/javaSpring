package org.pap.helper;

import javax.servlet.http.HttpSession;

import org.pap.domain.Person;
import org.pap.exceptions.DangerException;

public class RolHelper {

	public static void isRolOK(String rol, HttpSession s) throws DangerException{
		
		Person person = (Person)s.getAttribute("person");
			
			if (person == null) {
				if (rol!="anonymous") {
					PRG.error("Inadequate rol", "/");
				}
			}
			else {
				if ( !person.getNick().equals("admin") && rol.equals("admin") ) {
					PRG.error("Inadequate rol", "/");
			}		
		}
	}
}

