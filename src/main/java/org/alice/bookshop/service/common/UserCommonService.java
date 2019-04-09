package org.alice.bookshop.service.common;

import org.alice.bookshop.model.User;
import org.alice.bookshop.repository.UserJpa;
import org.alice.bookshop.security.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCommonService {

	@Autowired
	public UserJpa userJpa;

	public User getUserById(int id) {
		return userJpa.getOne(id);
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
}
