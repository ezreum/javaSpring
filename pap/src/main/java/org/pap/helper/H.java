package org.pap.helper;

import javax.servlet.http.HttpSession;

public class H {
	public static void info(HttpSession s, String mensaje, String severity, String link) {
		s.setAttribute("mensaje", mensaje);
		s.setAttribute("severity", severity);
		s.setAttribute("link", link);
	}
	
	public static void info(HttpSession s, String mensaje, String severity) {
		s.setAttribute("mensaje", mensaje);
		s.setAttribute("severity", severity);
		s.setAttribute("link", "/");
	}
	
	public static void info(HttpSession s, String mensaje) {
		s.setAttribute("mensaje", mensaje);
		s.setAttribute("severity", "/");
	}

}
