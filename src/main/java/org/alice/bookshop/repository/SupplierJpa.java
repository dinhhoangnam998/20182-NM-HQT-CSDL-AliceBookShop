package org.alice.bookshop.repository;

import java.util.List;

import org.alice.bookshop.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierJpa extends JpaRepository<Supplier, Integer>{

	Supplier findByName(String name);

	Page<Supplier> findByDeleted(boolean b, Pageable p);

	List<Supplier> findByDeleted(boolean b);

	
}
