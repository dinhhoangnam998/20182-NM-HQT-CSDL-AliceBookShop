package org.alice.bookshop.repository;

import org.alice.bookshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpa extends JpaRepository<User, Integer> {

	User findByUsername(String username);
}
