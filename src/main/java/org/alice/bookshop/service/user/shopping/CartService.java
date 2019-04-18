package org.alice.bookshop.service.user.shopping;

import java.util.ArrayList;
import java.util.List;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.model.Order;
import org.alice.bookshop.model.OrderLine;
import org.alice.bookshop.repository.BookJpa;
import org.alice.bookshop.repository.OrderJpa;
import org.alice.bookshop.repository.OrderLineJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("usCartService")
public class CartService {

	@Autowired
	public OrderJpa orderJpa;

	@Autowired
	public OrderLineJpa orderLineJpa;

	@Autowired
	public BookJpa bookJpa;

	public boolean add(int id, int bid, int q) {
		Order cart = orderJpa.getOne(id);
		int remain = bookJpa.getOne(bid).getRemainQuantity();
		if (q <= remain) {
			List<OrderLine> orderLines = cart.getOrderLines();

			boolean exited = false;
			int exitedLineId = 0;
			for (OrderLine line : orderLines) {
				if (line.getBook().getId() == bid) {
					exited = true;
					exitedLineId = line.getId();
					break;
				}
			}

			if (exited) {
				OrderLine orderLine = orderLineJpa.getOne(exitedLineId);
				orderLine.setQuantity(orderLine.getQuantity() + q);
				if (orderLine.getQuantity() > remain) {
					return false;
				} else {
					orderLineJpa.save(orderLine);
					return true;
				}
			} else {
				OrderLine newOrderLine = new OrderLine();
				newOrderLine.setBook(bookJpa.getOne(bid));
				newOrderLine.setQuantity(q);
				newOrderLine.setOrder(orderJpa.getOne(id));
				orderLineJpa.save(newOrderLine);
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean remove(int olid, int bid, int q) {
		OrderLine orderLine = orderLineJpa.getOne(olid);
		if (q > orderLine.getQuantity()) {
			return false;
		} else {
			orderLine.setQuantity(orderLine.getQuantity() - q);
		}
		return false;
	}

	public List<Integer> tryCheckout(int id) {
		List<Integer> invaidBIds = new ArrayList<Integer>();
		Order cart = orderJpa.getOne(id);
		List<OrderLine> ols = cart.getOrderLines();
		for (OrderLine ol : ols) {
			Book book = ol.getBook();
			if (ol.getQuantity() > book.getRemainQuantity()) {
				invaidBIds.add(ol.getId());
			}
		}
		return invaidBIds;
	}

	public void makeOrder(int id) {
		Order cart = orderJpa.getOne(id);
		List<OrderLine> ols = cart.getOrderLines();

		int total = 0;
		for (OrderLine ol : ols) {
			Book book = ol.getBook();
			total += book.getSalePrice() * ol.getQuantity();
		}
		cart.setTotal(total);
		cart.setState(1);
		orderJpa.save(cart);
	}
}
