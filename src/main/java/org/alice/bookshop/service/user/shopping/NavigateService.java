package org.alice.bookshop.service.user.shopping;

import java.util.ArrayList;
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
import org.springframework.stereotype.Service;

@Service("usCategoryService")
public class NavigateService {

	public final static int NUM_OF_ROW = 3;
	public final static int NUM_OF_COl = 4;

	@Autowired
	public AuthorJpa authorJpa;

	@Autowired
	public PublisherJpa publisherJpa;

	@Autowired
	public CategoryJpa categoryJpa;

	@Autowired
	public BookJpa bookJpa;

	public Page<Author> getAuthors(int p, int psize) {
		return authorJpa.findByDeleted(false, PageRequest.of(p - 1, psize));
	}

	public Page<Book> getBooksByAuthor(int auid, int requestPage, int pageSize) {
		return bookJpa.findByAuthor_IdAndDeleted(auid, false, PageRequest.of(requestPage - 1, pageSize));
	}

	public Page<Publisher> getPublisher(int requestPage, int pageSize) {
		return publisherJpa.findByDeleted(false, PageRequest.of(requestPage - 1, pageSize));
	}

	public Page<Book> getBooksByPublisher(int puid, int requestPage, int pageSize) {
		return bookJpa.findByPublisher_IdAndDeleted(puid, false, PageRequest.of(requestPage - 1, pageSize));
	}

	public Page<Book> getBooksByCategory(int caid, int requestPage, int pageSize) {
		return bookJpa.findByCategory_IdAndDeleted(caid, false, PageRequest.of(requestPage - 1, pageSize));
	}

	public Page<Category> getCategory(int requestPage, int pageSize) {
		return categoryJpa.findByDeleted(false, PageRequest.of(requestPage - 1, pageSize));
	}

	public <T> List<List<T>> getRows(List<T> tList) {
		List<List<T>> rows = new ArrayList<List<T>>();

		for (int i = 0; i <= NUM_OF_ROW - 1; i++) {
			List<T> row = new ArrayList<>();
			for (int j = i * NUM_OF_COl; j <= i * NUM_OF_COl + 3 && j <= tList.size() - 1; j++) {
				row.add(tList.get(j));
			}
			rows.add(row);
		}
		return rows;
	}

}
