package org.alice.bookshop.repository;

import org.alice.bookshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpa  extends JpaRepository<Order, Integer> {

}
