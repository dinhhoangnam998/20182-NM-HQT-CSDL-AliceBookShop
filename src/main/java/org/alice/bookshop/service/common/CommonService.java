package org.alice.bookshop.service.common;

import org.alice.bookshop.model.User;
import org.alice.bookshop.repository.UserJpa;
import org.alice.bookshop.security.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
	@Autowired
	private UserJpa userJpa;

	public User getUser() {

		String username = SpringSecurityUtil.getUsername();
		if (username.equals("null")) {
			User user = new User();
			user.setId(-1);
			return user;
		}

		return userJpa.findByUsername(username);
	}
}
