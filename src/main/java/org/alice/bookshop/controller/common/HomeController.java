package org.alice.bookshop.controller.common;

import java.util.List;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.service.common.HomeService;
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
	public String home(Model model) {

		List<Book> books1 = homeService.getBooks();
		List<Book> books2 = books1;
		List<Book> books3 = books2;

		model.addAttribute("category1", "Văn học");
		model.addAttribute("category2", "Sách thiếu nhi");
		model.addAttribute("category3", "Manga");
		model.addAttribute("books1", books1);
		model.addAttribute("books2", books2);
		model.addAttribute("books3", books3);
		return "common/home";
	}
}
