package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.service.admin.manage.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("amBookController") @RequestMapping("/admin/manage/book/")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping
	public String list(Model model, @RequestParam int p) {
		List<Book> books = bookService.getBooks(p);
		model.addAttribute("books", books);
		return "admin/manage/book/list";
	}
	
	@GetMapping("add")
	public String add() {
		
		return "admin/manage/book/add";
	}
	
	@PostMapping("add")
	public String add(Book book) {
		// check if success here!
		bookService.bookJpa.save(book);
		return "redirect:add";
		 
	}
	
	@GetMapping("edit/{id}")
	public String edit(Model model, @PathVariable int id) {
		
		Book book = bookService.bookJpa.getOne(id);
		model.addAttribute("book", book);
		return "admin/manage/book/edit";
	}
	
	@PostMapping("edit")
	public String edit(Book book) {
		// check if success here!
		bookService.bookJpa.save(book);
		return "";
	}
	
}
