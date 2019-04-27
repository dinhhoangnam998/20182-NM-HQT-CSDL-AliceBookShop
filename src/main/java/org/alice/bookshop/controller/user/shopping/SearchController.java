package org.alice.bookshop.controller.user.shopping;

import java.util.ArrayList;
import java.util.List;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.service.user.shopping.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;

	@ResponseBody
	@GetMapping("/books")
	public List<Book> liveSearch(@RequestParam String key) {
		List<Book> result = new ArrayList<Book>();
		result = searchService.getResult(key);
		return result;
	}
	
	@GetMapping("/books/search")
	public String search(Model model, @RequestParam String key) {
		model.addAttribute("books", searchService.getResult(key));
		model.addAttribute("key", key);
		return "user/shopping/search-result";
	}
}
