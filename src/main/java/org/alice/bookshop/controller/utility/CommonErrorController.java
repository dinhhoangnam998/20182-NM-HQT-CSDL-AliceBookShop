package org.alice.bookshop.controller.utility;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonErrorController {

	@GetMapping("/403")
	public String e403() {
		return "/error/403";
	}
//
//	@GetMapping("/404")
//	public String e404() {
//		return "/common/404";
//	}
//	
//	@GetMapping("/500")
//	public String e500() {
//		return "/common/error";
//	}
//	
//	@GetMapping("/error")
//	public String e() {
//		return "/common/error";
//	}
}
