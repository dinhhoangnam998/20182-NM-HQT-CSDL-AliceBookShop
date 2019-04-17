package org.alice.bookshop.repository;

import org.alice.bookshop.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpa extends JpaRepository<Order, Integer> {

	Page<Order> findByStateNot(int i, Pageable pageable);

	long countByState(int i);

}
