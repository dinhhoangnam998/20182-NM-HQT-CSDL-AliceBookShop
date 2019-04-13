package org.alice.bookshop.service.admin.manage;

import org.alice.bookshop.repository.Book_InputJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("amBook_InputService")
public class Book_InputService {

	@Autowired
	public Book_InputJpa book_inputJpa;
}
