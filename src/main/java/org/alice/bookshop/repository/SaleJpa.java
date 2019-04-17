package org.alice.bookshop.repository;

import org.alice.bookshop.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleJpa extends JpaRepository<Sale, Integer> {

	Page<Sale> findByDeleted(boolean b, Pageable pageable);

}
