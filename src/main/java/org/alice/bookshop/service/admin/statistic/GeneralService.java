package org.alice.bookshop.service.admin.statistic;

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

	public long getNumberOfBook() {
		return bookJpa.count();
	}

	public long getTotalOfBook() {
		return 0;
	}

	public long getNumberOfSuccessedOrder() {
		return orderJpa.countByState(3);
	}

	public long getNumberOfOrder() {
		return orderJpa.count();
	}

	public long getNumberOfCanceledOrder() {
		return orderJpa.countByState(-1);
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

}
