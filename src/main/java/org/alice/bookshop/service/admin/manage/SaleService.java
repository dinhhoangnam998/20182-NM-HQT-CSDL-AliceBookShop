package org.alice.bookshop.service.admin.manage;

import org.alice.bookshop.model.Book_Sale;
import org.alice.bookshop.model.Sale;
import org.alice.bookshop.repository.SaleJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service("amSaleService")
public class SaleService {
	@Autowired
	public SaleJpa saleJpa;

	@Autowired
	public Book_SaleService bsService;

	public Page<Sale> getSales(int p, int psize) {
		return saleJpa.findByDeleted(false, PageRequest.of(p - 1, psize));
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
			bs.setSale(sale);
			bsService.book_saleJpa.save(bs);
		}
		return "Edit sale id = " + sale.getId() + " successed";
	}
}
