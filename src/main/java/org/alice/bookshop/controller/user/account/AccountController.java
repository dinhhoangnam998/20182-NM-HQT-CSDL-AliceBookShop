package org.alice.bookshop.controller.user.account;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.alice.bookshop.model.User;
import org.alice.bookshop.service.user.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("uaAccountController")
public class AccountController {

	@Autowired
	private AccountService accountService;

	List<String> errMsgs = new ArrayList<>();

	@GetMapping("/login")
	public String login(Model model) {
		return "user/account/login";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("errMsgs", errMsgs);
		model.addAttribute("user", new User());
		return "user/account/signup";
	}

	@PostMapping("/signup")
	public String signup(Model model, User user) {
		errMsgs = accountService.validateSignUpAccount(user);
		if (errMsgs.size() == 0) {
			accountService.signup(user);
			return "redirect:/signup-success";
		} else {
			return "redirect:/signup";
		}

	}

	@GetMapping("signup-success")
	public String signupSuccess() {
		return "user/account/signup-success";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/home?login=false";
	}

	@GetMapping("/profiles/{id}")
	public String profile(Model model, @PathVariable int id, @RequestParam(required = false) String msg) {
		// handle invalid modify here
		User user = accountService.getUserById(id);
		model.addAttribute("user", user);
		return "user/account/profile";
	}

	@PostMapping("/profiles/{id}")
	public String profile(User user) {
		String msg = accountService.validateModifyProfile(user);
		if (msg.equals("ok")) {
			return "redirect:/profiles/" + user.getId();
		} else {
			return "redirect:/profiles/" + user.getId() + "?msg=" + msg;
		}

	}

}
