package org.alice.bookshop.controller.utility;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonErrorController {

	@GetMapping("/403")
	public String e403() {
		return "/common/403";
	}

	@GetMapping("/404")
	public String e404() {
		return "/common/404";
	}
}
