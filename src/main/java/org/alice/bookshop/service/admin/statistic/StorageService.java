package org.alice.bookshop.service.admin.statistic;

import java.util.List;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.repository.BookJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("asBookService")
public class StorageService {

	@Autowired
	public BookJpa bookJpa;

	public List<Book> getBookRemainUnderLimit(int limit, int p, int psize) {
		Pageable pageable = PageRequest.of(p - 1, psize);
		Page<Book> books = bookJpa.findByRemainQuantityLessThanEqual(limit, pageable);
		return books.getContent();
	}

	public long getNumberOfRecord(int limit) {
		return bookJpa.countByRemainQuantityLessThanEqual(limit);
	}

}
