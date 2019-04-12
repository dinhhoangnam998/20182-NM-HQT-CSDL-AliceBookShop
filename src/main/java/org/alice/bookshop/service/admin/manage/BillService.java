package org.alice.bookshop.service.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Bill;
import org.alice.bookshop.repository.BillJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("amBillService")
public class BillService {
	@Autowired
	private BillJpa billJpa;

	public List<Bill> getBills(int p, int psize) {
		Pageable pageable = PageRequest.of(p - 1, psize);
		Page<Bill> authors = billJpa.findAll(pageable);
		return authors.getContent();
	}

	public long getTotalPage(int psize) {
		long total = billJpa.count();
		long totalPage = total / psize;
		if (total % psize != 0) {
			totalPage += 1;
		}
		return totalPage;
	}
}
