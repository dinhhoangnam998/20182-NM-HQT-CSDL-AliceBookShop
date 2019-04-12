package org.alice.bookshop.service.user.shopping;

import org.alice.bookshop.repository.BookJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("usBookService")
public class BookInfoService {

	@Autowired
	public BookJpa bookJpa;
}
