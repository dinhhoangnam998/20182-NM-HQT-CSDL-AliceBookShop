package org.alice.bookshop.repository;

import java.util.Date;
import java.util.List;

import org.alice.bookshop.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpa extends JpaRepository<Order, Integer> {

	Page<Order> findByStateNot(int i, Pageable pageable);

	long countByState(int i);

	long countByOrderDateGreaterThanEqualAndOrderDateLessThanEqual(Date begin2, Date end2);

	long countByOrderDateGreaterThanEqualAndOrderDateLessThanEqualAndState(Date begin2, Date end2, int i);

	Page<Order> findByOrderDateGreaterThanEqualAndOrderDateLessThanEqualAndStateNot(Date begin, Date end, int i,
			Pageable pageable);

	long countByOrderDateGreaterThanEqualAndOrderDateLessThanEqualAndStateNot(Date begin2, Date end2, int i);

	List<Order> findByOrderDateGreaterThanEqualAndOrderDateLessThanEqualAndState(Date begin2, Date end2, int i);

	long countByStateNot(int i);

	Order findByUser_IdAndState(int id, int i);

	List<Order> findByUser_Id(int uid);

	List<Order> findByUser_IdAndStateNot(int uid, int state);

}
