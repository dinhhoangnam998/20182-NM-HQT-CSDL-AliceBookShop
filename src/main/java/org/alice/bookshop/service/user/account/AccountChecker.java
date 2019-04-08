package org.alice.bookshop.service.user.account;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.alice.bookshop.model.User;
import org.alice.bookshop.repository.UserJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountChecker {

	@Autowired
	private UserJpa userJpa;

	public String checkUsername(String username) {
		User user = userJpa.findByUsername(username);
		if (user != null) {
			return "username already exits!";
		} else {
			return "ok";
		}
	}

	public String checkPassword(String password, String confirmPassword) {
		if (!password.equals(confirmPassword)) {
			return "password is not match!";
		} else if (password.length() < 8) {
			return "password must more than 8 character!";
		} else {
			return "ok";
		}
	}

	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public String checkEmail(String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		if (!matcher.matches()) {
			return "email is not valid!";
		} else {
			return "ok";
		}

	}

	public String checkPhone(String phone) {
		pattern = Pattern.compile("\\d{3}\\d{7}");
		matcher = pattern.matcher(phone);
		if (!matcher.matches()) {
			return "phone number is not valid!";
		} else {
			return "ok";
		}
	}

}
