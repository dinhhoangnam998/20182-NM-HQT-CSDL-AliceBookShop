package org.alice.bookshop.repository;

import java.util.List;

import org.alice.bookshop.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpa extends JpaRepository<Category, Integer> {

	Category findByName(String name);

	Page<Category> findByDeleted(boolean b, Pageable pageable);

	List<Category> findByDeleted(boolean b);

}
