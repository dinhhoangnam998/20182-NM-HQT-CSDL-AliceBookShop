package org.alice.bookshop.service.admin.manage;

import org.alice.bookshop.repository.Book_SaleJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("amBook_SaleService")
public class Book_SaleService {
	
	@Autowired
	public Book_SaleJpa book_saleJpa;

}
