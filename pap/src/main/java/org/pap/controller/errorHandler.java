package org.pap.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class errorHandler implements ErrorController{

	private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error(ModelMap m) {
    	m.put("view", "_error/HandlerDemo");
        return "/_t/frame";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
	
}
