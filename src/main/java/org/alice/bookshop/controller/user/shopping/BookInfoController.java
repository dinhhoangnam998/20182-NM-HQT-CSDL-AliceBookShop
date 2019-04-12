package org.alice.bookshop.controller.user.shopping;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.service.user.shopping.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller("usBookController")
public class BookInfoController {

	@Autowired
	private BookInfoService bookInfoService;
	
	@GetMapping("/books/{id}")
	public String bookInfo(Model model, @PathVariable int id) {
		Book book = bookInfoService.bookJpa.getOne(id);
		model.addAttribute("book", book);
		return "/user/shopping/bookinfo/book-info";
	}
}
