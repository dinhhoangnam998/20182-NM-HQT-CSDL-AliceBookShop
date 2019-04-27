package org.alice.bookshop.service.user.shopping;

import java.util.List;

import org.alice.bookshop.model.Order;
import org.alice.bookshop.repository.OrderJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingHistoryService {

	@Autowired
	public OrderJpa orderJpa;

	public List<Order> getHistoryShopping(int uid) {
		return orderJpa.findByUser_IdAndStateNot(uid, 0);
	}

}
