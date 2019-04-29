package org.alice.bookshop.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	private HomeService homeService;

	@GetMapping
	public String home(Model model, HttpSession ss) {

		List<Book> books1 = homeService.getBooks(1, 5);
		List<Book> books2 = homeService.getBooks(2, 5);
		List<Book> books3 = homeService.getBooks(3, 5);

		model.addAttribute("category1", "Truyện hot");
		model.addAttribute("category2", "Truyện mới");
		model.addAttribute("category3", "Manga hay");
		model.addAttribute("books1", books1);
		model.addAttribute("books2", books2);
		model.addAttribute("books3", books3);

		ss.setAttribute("categories", homeService.getCategories());
		ss.setAttribute("publishers", homeService.getPublishers());
		ss.setAttribute("authors", homeService.getAuthors());
		return "common/home";
	}
}
