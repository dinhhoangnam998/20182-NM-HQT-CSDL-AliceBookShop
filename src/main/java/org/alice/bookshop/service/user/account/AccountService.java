package org.alice.bookshop.service.user.account;

import org.alice.bookshop.model.User;
import org.alice.bookshop.repository.UserJpa;
import org.alice.bookshop.security.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("uaAccountService")
public class AccountService {

	@Autowired
	private UserJpa userJpa;

	@Autowired
	PasswordEncoder passwordEncoder;

	public String validateSignUp(User user) {
		// logic here
		return "ok";
	}

	public void signup(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userJpa.save(user);
	}

	public User getUser() {

		String username = SpringSecurityUtil.getUsername();
		if (username.equals("null")) {
			User user = new User();
			user.setId(-1);
			return user;
		}

		return userJpa.findByUsername(username);
	}

	public User getUserById(int id) {
		return userJpa.getOne(id);
	}

	public String validateModifyProfile(User user) {
		// logic here
		return "ok";
	}

}
