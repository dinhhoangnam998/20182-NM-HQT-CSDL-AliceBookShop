package org.alice.bookshop.repository;

import org.alice.bookshop.model.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDetailJpa extends JpaRepository<BillDetail, Integer> {

}
