package org.alice.bookshop.repository;

import org.alice.bookshop.model.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherJpa extends JpaRepository<Publisher, Integer> {

	Publisher findByName(String name);

	Page<Publisher> findByDeleted(boolean b, Pageable pageable);

}
