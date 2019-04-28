package org.alice.bookshop.controller.user.shopping;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.Author;
import org.alice.bookshop.model.Book;
import org.alice.bookshop.model.Category;
import org.alice.bookshop.model.Publisher;
import org.alice.bookshop.service.user.shopping.NavigateService;
import org.alice.bookshop.service.utility.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("usNavigateController")
public class NavigateController {

	private final static int DEFAULT_PAGE_SIZE = 12;;

	@Autowired
	private NavigateService navigateService;

	@Autowired
	private PaginationService pagi;

	@GetMapping("/authors")
	public String showAuthors(Model model, HttpSession ss, @RequestParam(required = false, defaultValue = "1") int p) {
		pagi.validate(ss, p, DEFAULT_PAGE_SIZE);
		Page<Author> authors = navigateService.getAuthors(pagi.getRequestPage(), pagi.getPageSize());
		List<List<Author>> authorRows = navigateService.getRows(authors.getContent());
		model.addAttribute("rows", authorRows);
		List<Integer> pageList = pagi.getPageList(authors.getTotalPages());
		model.addAttribute("pages", pageList);

		return "/user/shopping/navigate/author";
	}

	@GetMapping("books/au/{auid}")
	public String showBooksOfAuthor(Model model, HttpSession ss, @PathVariable int auid,
			@RequestParam(required = false, defaultValue = "1") int p) {
		pagi.validate(ss, p, DEFAULT_PAGE_SIZE);
		Page<Book> books = navigateService.getBooksByAuthor(auid, pagi.getRequestPage(), pagi.getPageSize());
		List<List<Book>> booksRows = navigateService.getRows(books.getContent());
		model.addAttribute("rows", booksRows);
		List<Integer> pageList = pagi.getPageList(books.getTotalPages());
		model.addAttribute("pages", pageList);
		model.addAttribute("auid", auid);
		return "/user/shopping/navigate/book-by-author";
	}

	@GetMapping("/publishers")
	public String showPublishers(Model model, HttpSession ss,
			@RequestParam(required = false, defaultValue = "1") int p) {
		pagi.validate(ss, p, DEFAULT_PAGE_SIZE);
		Page<Publisher> publishers = navigateService.getPublisher(pagi.getRequestPage(), pagi.getPageSize());
		List<List<Publisher>> rows = navigateService.getRows(publishers.getContent());
		model.addAttribute("rows", rows);
		List<Integer> pageList = pagi.getPageList(publishers.getTotalPages());
		model.addAttribute("pages", pageList);

		return "/user/shopping/navigate/publisher";
	}

	@GetMapping("books/pu/{puid}")
	public String showBooksOfPublisher(Model model, HttpSession ss, @PathVariable int puid,
			@RequestParam(required = false, defaultValue = "1") int p) {
		pagi.validate(ss, p, DEFAULT_PAGE_SIZE);
		Page<Book> books = navigateService.getBooksByPublisher(puid, pagi.getRequestPage(), pagi.getPageSize());
		List<List<Book>> booksRows = navigateService.getRows(books.getContent());
		model.addAttribute("rows", booksRows);
		List<Integer> pageList = pagi.getPageList(books.getTotalPages());
		model.addAttribute("pages", pageList);
		model.addAttribute("puid", puid);
		return "/user/shopping/navigate/book-by-publisher";
	}

	@GetMapping("/categories")
	public String showCategories(Model model, HttpSession ss,
			@RequestParam(required = false, defaultValue = "1") int p) {
		pagi.validate(ss, p, 20);
		Page<Category> categories = navigateService.getCategory(pagi.getRequestPage(), pagi.getPageSize());
		model.addAttribute("categories", categories.getContent());
		List<Integer> pageList = pagi.getPageList(categories.getTotalPages());
		model.addAttribute("pages", pageList);

		return "/user/shopping/navigate/category";
	}

	@GetMapping("books/ca/{caid}")
	public String showBooksOfCategory(Model model, HttpSession ss, @PathVariable int caid,
			@RequestParam(required = false, defaultValue = "1") int p) {
		pagi.validate(ss, p, DEFAULT_PAGE_SIZE);
		Page<Book> books = navigateService.getBooksByCategory(caid, pagi.getRequestPage(), pagi.getPageSize());
		List<List<Book>> booksRows = navigateService.getRows(books.getContent());
		model.addAttribute("rows", booksRows);
		List<Integer> pageList = pagi.getPageList(books.getTotalPages());
		model.addAttribute("pages", pageList);
		model.addAttribute("caid", caid);
		return "/user/shopping/navigate/book-by-category";
	}

}
