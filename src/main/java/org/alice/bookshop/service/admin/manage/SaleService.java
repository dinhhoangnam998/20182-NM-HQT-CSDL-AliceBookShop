package org.alice.bookshop.service.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Book_Sale;
import org.alice.bookshop.model.Sale;
import org.alice.bookshop.repository.SaleJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("amSaleService")
public class SaleService {
	@Autowired
	public SaleJpa saleJpa;

	@Autowired
	private Book_SaleService bsService;

	public List<Sale> getSales(int p, int psize) {
		Pageable pageable = PageRequest.of(p - 1, psize);
		Page<Sale> sales = saleJpa.findAll(pageable);
		return sales.getContent();
	}

	public String add(Sale sale) {
		saleJpa.save(sale);

		for (Book_Sale bs : sale.getBook_sales()) {
			bs.setSale(sale);
			bsService.book_saleJpa.save(bs);
		}
		return "Add sale " + sale.getId() + " successed";
	}

	public String edit(Sale sale) {
		saleJpa.save(sale);
		for (Book_Sale bs : sale.getBook_sales()) {
			bsService.book_saleJpa.save(bs);
		}
		return "Edit sale id = " + sale.getId() + " successed";
	}
}
