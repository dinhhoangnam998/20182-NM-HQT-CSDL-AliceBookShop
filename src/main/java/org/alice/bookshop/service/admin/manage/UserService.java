package org.alice.bookshop.service.admin.manage;

import java.util.List;

import org.alice.bookshop.model.User;
import org.alice.bookshop.repository.UserJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("am")
public class UserService {
	
	@Autowired
	private UserJpa userJpa;
	
	public List<User> getUsers(int p, int psize) {
		Pageable pageable = PageRequest.of(p - 1, psize);
		Page<User> users = userJpa.findAll(pageable);
		return users.getContent();
	}
	
	public long getTotalPage(int psize) {
		long total = userJpa.count();
		long totalPage = total / psize;
		if (total % psize != 0) {
			totalPage += 1;
		}
		return totalPage;
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
