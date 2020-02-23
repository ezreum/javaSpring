package org.pap.helper;

import org.pap.exceptions.DangerException;
import org.pap.exceptions.InfoException;

public class PRG {
	
	public static void info(String message, String link) throws InfoException {
		throw new InfoException(message+"@"+link);
	}
	
	public static void info(String message) throws InfoException {
		throw new InfoException(message);
	}
	
	public static void error(String message, String link) throws DangerException {
		throw new DangerException(message+"@"+link);
	}
	
	public static void error(String message) throws DangerException {
		throw new DangerException(message);
	}
	
}
