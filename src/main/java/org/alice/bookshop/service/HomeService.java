package org.alice.bookshop.service;

import java.util.List;

import org.alice.bookshop.model.Author;
import org.alice.bookshop.model.Book;
import org.alice.bookshop.model.Category;
import org.alice.bookshop.model.Publisher;
import org.alice.bookshop.repository.AuthorJpa;
import org.alice.bookshop.repository.BookJpa;
import org.alice.bookshop.repository.CategoryJpa;
import org.alice.bookshop.repository.PublisherJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

	@Autowired
	public BookJpa bookJpa;
	
	@Autowired
	public CategoryJpa categoryJpa;
	
	@Autowired
	public AuthorJpa authorJpa;
	
	@Autowired
	public PublisherJpa publisherJpa;

	public List<Book> getBooks(int p, int size) {
		Pageable pageable = PageRequest.of(p - 1, size);
		Page<Book> books = bookJpa.findAll(pageable);
		return books.getContent();
	}

	public List<Category> getCategories() {
		return categoryJpa.findByDeleted(false);
	}

	public List<Publisher> getPublishers() {
		return publisherJpa.findByDeleted(false);
	}

	public List<Author> getAuthors() {
		return authorJpa.findByDeleted(false);
	}

}
