package ifrn.projeto.finolanche.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndeController {

	@GetMapping("/")
	public String index() {
		return "/site/index";
	}	
	
}
