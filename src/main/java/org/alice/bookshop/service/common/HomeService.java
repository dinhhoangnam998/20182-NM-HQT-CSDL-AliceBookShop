package org.alice.bookshop.service.common;

import java.util.List;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.repository.BookJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

	@Autowired
	public BookJpa bookJpa;

	public List<Book> getBooks() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Book> books = bookJpa.findAll(pageable);
		return books.getContent();
	}

}
