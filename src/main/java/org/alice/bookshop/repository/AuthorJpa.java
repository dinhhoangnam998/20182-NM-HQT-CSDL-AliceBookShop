package org.alice.bookshop.repository;

import java.util.List;

import org.alice.bookshop.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorJpa extends JpaRepository<Author, Integer> {

	Author findByName(String name);

	Page<Author> findByDeleted(boolean b, Pageable pageable);

	List<Author> findByDeleted(boolean b);

}
