package org.alice.bookshop.service.admin.manage;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.model.Book_Input;
import org.alice.bookshop.model.Input;
import org.alice.bookshop.repository.BookJpa;
import org.alice.bookshop.repository.Book_InputJpa;
import org.alice.bookshop.repository.InputJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service("amInputService")
public class InputService {
	@Autowired
	public InputJpa inputJpa;

	@Autowired
	public Book_InputService biService;

	@Autowired
	public BookJpa bookJpa;

	@Autowired
	public Book_InputJpa b_iJpa;

	public Page<Input> getInputs(int p, int psize) {

		return inputJpa.findByDeleted(false, PageRequest.of(p, psize));
	}

	public String add(Input input) {
		inputJpa.save(input);
		for (Book_Input bi : input.getBook_inputs()) {
			bi.setInput(input);
//			does not use any more because use trigger instead
//			int bookId = bi.getBook().getId();
//			Book book = bookJpa.getOne(bookId);
//			book.setRemainQuantity(book.getRemainQuantity() + bi.getQuantity());
//			bookJpa.save(book);
			biService.book_inputJpa.save(bi);
		}
		return "Add input " + input.getId() + " successed";
	}

	public String edit(Input input) {
		inputJpa.save(input);
		for (Book_Input bi : input.getBook_inputs()) {
			bi.setInput(input);
			Book_Input origin = b_iJpa.getOne(bi.getId());
			int originQuantity = origin.getQuantity();
			int bookId = bi.getBook().getId();
			Book book = bookJpa.getOne(bookId);
			book.setRemainQuantity(book.getRemainQuantity() + bi.getQuantity() - originQuantity);
			bookJpa.save(book);
			biService.book_inputJpa.save(bi);
		}
		return "Edit input id = " + input.getId() + " successed";
	}
}
