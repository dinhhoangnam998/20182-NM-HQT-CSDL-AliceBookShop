package org.alice.bookshop.repository;

import org.alice.bookshop.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorJpa extends JpaRepository<Author, Integer> {

}
