package com.pmg;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {
	
	@GetMapping("/pmg")
	public String pmg() {
		String forwardPath = "pmg";
		return forwardPath;
	}
}
