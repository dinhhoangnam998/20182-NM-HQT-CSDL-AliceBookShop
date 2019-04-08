package org.alice.bookshop.controller.common;

import org.alice.bookshop.model.User;
import org.alice.bookshop.service.user.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
	@Autowired
	private AccountService accountService;

	@GetMapping
	public String home(Model model) {
		User user = accountService.getUser();
		model.addAttribute("user", user);
		return "common/home";
	}
}
