package org.alice.bookshop.repository;

import java.util.List;

import org.alice.bookshop.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpa extends JpaRepository<Book, Integer> {

	Book findByName(String name);

	Page<Book> findByRemainQuantityLessThanEqual(int limit, Pageable pageable);

	long countByRemainQuantityLessThanEqual(int limit);

	Page<Book> findByDeleted(boolean b, Pageable pageable);

	Page<Book> findByRemainQuantityLessThanEqualAndDeleted(int limit, boolean b, Pageable pageable);

	Object countByDeleted(boolean b);

	List<Book> findByDeleted(boolean b);

	List<Book> findByNameContainingAndDeleted(String key, boolean b);

	List<Book> findByNameLikeAndDeleted(String key, boolean b);

	List<Book> findByNameStartingWithAndDeleted(String key, boolean b);

}
