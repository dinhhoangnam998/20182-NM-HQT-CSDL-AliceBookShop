package org.alice.bookshop.repository;

import org.alice.bookshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpa extends JpaRepository<Category, Integer> {

}
