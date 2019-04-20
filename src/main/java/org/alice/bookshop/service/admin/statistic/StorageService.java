package org.alice.bookshop.service.admin.statistic;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.repository.BookJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service("asBookService")
public class StorageService {

	@Autowired
	public BookJpa bookJpa;

	public Page<Book> getBookRemainUnderLimit(int limit, int p, int psize) {

		return bookJpa.findByRemainQuantityLessThanEqualAndDeleted(limit, false, PageRequest.of(p - 1, psize));
	}

}
