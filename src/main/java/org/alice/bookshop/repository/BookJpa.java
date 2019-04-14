package org.alice.bookshop.repository;

import org.alice.bookshop.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpa extends JpaRepository<Book, Integer> {

	Book findByName(String name);

	Page<Book> findByRemainQuantityLessThanEqual(int limit, Pageable pageable);

	long countByRemainQuantityLessThanEqual(int limit);

}
