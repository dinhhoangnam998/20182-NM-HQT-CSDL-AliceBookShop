package org.alice.bookshop.service.user.shopping;

import java.util.List;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.repository.BookJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

	@Autowired
	public BookJpa bookJpa;

	public List<Book> getResult(String key) {
		
		return bookJpa.findByNameContainingAndDeleted(key, false);
	}
}
