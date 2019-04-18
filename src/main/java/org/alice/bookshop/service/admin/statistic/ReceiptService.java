package org.alice.bookshop.service.admin.statistic;

import java.util.Date;
import java.util.List;

import org.alice.bookshop.model.Order;
import org.alice.bookshop.repository.OrderJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service("asReceiptService")
public class ReceiptService {

	@Autowired
	private OrderJpa orderJpa;

	public Page<Order> getReceipts(Date begin, Date end, int p, int psize) {

		return orderJpa.findByOrderDateGreaterThanEqualAndOrderDateLessThanEqualAndStateNot(begin, end, 0,
				PageRequest.of(p - 1, psize));
	}

	public long getTotalNumberOfOrder(Date begin2, Date end2) {
		return orderJpa.countByOrderDateGreaterThanEqualAndOrderDateLessThanEqualAndStateNot(begin2, end2, 0);
	}

	public long getNumberOfSuccessed(Date begin2, Date end2) {
		return orderJpa.countByOrderDateGreaterThanEqualAndOrderDateLessThanEqualAndState(begin2, end2, 4);
	}

	public long getNumberOfCanceled(Date begin2, Date end2) {
		return orderJpa.countByOrderDateGreaterThanEqualAndOrderDateLessThanEqualAndState(begin2, end2, 3);
	}

	public long getNumberOfDelevering(Date begin2, Date end2) {
		return orderJpa.countByOrderDateGreaterThanEqualAndOrderDateLessThanEqualAndState(begin2, end2, 2);
	}

	public long getReceipt(Date begin2, Date end2) {
		List<Order> successedOrders = orderJpa.findByOrderDateGreaterThanEqualAndOrderDateLessThanEqualAndState(begin2,
				end2, 2);
		long receipt = 0;
		for (Order order : successedOrders) {
			receipt += order.getTotal();
		}
		return receipt;
	}

	public long getNumberOfNewOrder(Date begin, Date end) {
		return orderJpa.countByOrderDateGreaterThanEqualAndOrderDateLessThanEqualAndState(begin, end, 1);
	}

}
