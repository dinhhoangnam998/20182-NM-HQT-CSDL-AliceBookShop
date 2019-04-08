package org.alice.bookshop.service.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.repository.BookJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("amBookService")
public class BookService {

	@Autowired
	public BookJpa bookJpa;

	public List<Book> getBooks(int p) {
		// TODO Auto-generated method stub
		return null;
	}
}
