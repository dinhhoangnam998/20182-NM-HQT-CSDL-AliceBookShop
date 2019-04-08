package org.alice.bookshop.controller.user.account;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.User;
import org.alice.bookshop.service.common.CommonService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("uaAccountController")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private CommonService cmService;

	@GetMapping("/login")
	public String login(Model model) {
		return "user/account/login";
	}

	@GetMapping("/login-success")
	public String loginSuccess(HttpSession session) {
		session.setAttribute("user", cmService.getUser());
		return "redirect:/home";
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("user", new User());
		return "user/account/signup";
	}

	@PostMapping("/signup")
	public String signup(RedirectAttributes redirAttr, User user) {
		List<String> msgs = accountService.validateSignUpAccount(user);
		if (msgs.size() == 0) {
			accountService.signup(user);
			redirAttr.addFlashAttribute("user", user);
			return "redirect:/signup-success";
		} else {
			redirAttr.addFlashAttribute("msgs", msgs);
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
		return "redirect:/home";
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
