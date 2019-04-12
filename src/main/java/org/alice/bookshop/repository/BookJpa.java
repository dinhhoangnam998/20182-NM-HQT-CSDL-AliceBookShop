package org.alice.bookshop.repository;

import org.alice.bookshop.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpa extends JpaRepository<Book, Integer> {

	Book findByName(String name);

}
