package org.alice.bookshop.service.admin.statistic;

import java.util.List;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.repository.AuthorJpa;
import org.alice.bookshop.repository.BookJpa;
import org.alice.bookshop.repository.Book_InputJpa;
import org.alice.bookshop.repository.CategoryJpa;
import org.alice.bookshop.repository.InputJpa;
import org.alice.bookshop.repository.OrderJpa;
import org.alice.bookshop.repository.PublisherJpa;
import org.alice.bookshop.repository.SaleJpa;
import org.alice.bookshop.repository.UserJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("asGeneralService")
public class GeneralService {

	@Autowired
	public UserJpa userJpa;

	@Autowired
	public BookJpa bookJpa;

	@Autowired
	public OrderJpa orderJpa;

	@Autowired
	public AuthorJpa authorJpa;

	@Autowired
	public CategoryJpa categoryJpa;

	@Autowired
	public PublisherJpa publisherJpa;

	@Autowired
	public InputJpa inputJpa;

	@Autowired
	public SaleJpa saleJpa;

	@Autowired
	public Book_InputJpa book_inputJpa;

	public long getNumberOfUser() {
		return userJpa.count();
	}

	public long getNumberOfBlockedUser() {
		return userJpa.countByPrivilege(-1);
	}

	public long getNumberOfOrder() {
		return orderJpa.countByStateNot(0);
	}

	public Object getNumberOfNewOrder() {
		return orderJpa.countByState(1);
	}

	public Object getNumberOfDelevering() {
		return orderJpa.countByState(2);
	}

	public long getNumberOfCanceledOrder() {
		return orderJpa.countByState(3);
	}

	public long getNumberOfSuccessedOrder() {
		return orderJpa.countByState(4);
	}

	public long getNumberOfAuthor() {
		return authorJpa.count();
	}

	public long getNumberOfPublisher() {
		return publisherJpa.count();
	}

	public long getNumberOfInput() {
		return inputJpa.count();
	}

	public long getNumberOfSale() {
		return saleJpa.count();
	}

	public Object getNumberOfCategory() {

		return categoryJpa.count();
	}

	public Object getTotalBook() {
		return bookJpa.count();
	}

	public Object getDeleteBook() {
		return bookJpa.countByDeleted(true);
	}

	public Object getRemainBook() {
		return bookJpa.countByDeleted(false);
	}

	public Object getTotalRemainBook() {
		List<Book> books = bookJpa.findByDeleted(false);
		int totalRemain = 0;
		for (Book b : books) {
			totalRemain += b.getRemainQuantity();
		}
		return totalRemain;
	}

}
