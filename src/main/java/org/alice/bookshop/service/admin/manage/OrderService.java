package org.alice.bookshop.service.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Order;
import org.alice.bookshop.repository.OrderJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("amOrderService")
public class OrderService {
	@Autowired
	public OrderJpa orderJpa;

	public List<Order> getOrders(int p, int psize) {
		Pageable pageable = PageRequest.of(p - 1, psize);
		Page<Order> authors = orderJpa.findAll(pageable);
		return authors.getContent();
	}
}
