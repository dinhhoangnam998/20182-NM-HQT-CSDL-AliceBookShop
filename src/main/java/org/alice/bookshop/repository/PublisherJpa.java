package org.alice.bookshop.repository;

import org.alice.bookshop.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherJpa extends JpaRepository<Publisher, Integer> {

}
