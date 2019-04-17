package org.alice.bookshop.service.admin.manage;

import org.alice.bookshop.model.User;
import org.alice.bookshop.repository.UserJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service("am")
public class UserService {

	@Autowired
	public UserJpa userJpa;

	public Page<User> getUsers(int p, int psize) {
		return userJpa.findAll(PageRequest.of(p - 1, psize));
	}

	public User block(int id) {
		User user = userJpa.getOne(id);
		user.setPrivilege(-1);
		return userJpa.save(user);
	}

	public User unblock(int id) {
		User user = userJpa.getOne(id);
		user.setPrivilege(0);
		return userJpa.save(user);
	}
}
