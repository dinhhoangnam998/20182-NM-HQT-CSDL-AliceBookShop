package org.alice.bookshop.repository;

import org.alice.bookshop.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineJpa extends JpaRepository<OrderLine, Integer> {

}
