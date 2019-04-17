package org.alice.bookshop.repository;

import org.alice.bookshop.model.Input;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InputJpa extends JpaRepository<Input, Integer> {

	Page<Input> findByDeleted(boolean b, Pageable pageable);

}
