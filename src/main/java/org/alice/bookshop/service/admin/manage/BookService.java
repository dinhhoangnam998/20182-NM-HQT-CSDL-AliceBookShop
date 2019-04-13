package org.alice.bookshop.service.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.repository.BookJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("amBookService")
public class BookService {

	@Autowired
	public BookJpa bookJpa;

	public List<Book> getBooks(int p, int psize) {
		Pageable pageable = PageRequest.of(p - 1, psize);
		Page<Book> books = bookJpa.findAll(pageable);
		return books.getContent();
	}

	private boolean isBookExit(Book book) {
		Book isExit = bookJpa.findByName(book.getName());
		return (isExit != null);
	}

	public String add(Book book) {
		if (isBookExit(book)) {
			return "Book " + book.getName() + " already exit";
		} else {
			bookJpa.save(book);
			return "Add book successed";
		}
	}

	public String edit(Book book) {
		Book origin = bookJpa.getOne(book.getId());
		if (!origin.getName().equals(book.getName()) && isBookExit(book)) {
			return "Book's name should not be same with another!";
		} else {

			bookJpa.save(book);
			return "Edit book id = " + book.getId() + " successed";
		}
	}

}
