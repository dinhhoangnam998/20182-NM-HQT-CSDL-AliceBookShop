package org.alice.bookshop.service.admin.manage;

import org.alice.bookshop.model.Order;
import org.alice.bookshop.repository.OrderJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service("amOrderService")
public class OrderService {
	@Autowired
	public OrderJpa orderJpa;

	public Page<Order> getOrders(int p, int psize) {
		return orderJpa.findByStateNot(0, PageRequest.of(p - 1, psize));
	}
}
