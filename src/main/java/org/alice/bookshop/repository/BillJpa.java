package org.alice.bookshop.repository;

import org.alice.bookshop.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillJpa  extends JpaRepository<Bill, Integer> {

}
