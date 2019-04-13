package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.service.admin.manage.AuthorService;
import org.alice.bookshop.service.admin.manage.BookService;
import org.alice.bookshop.service.admin.manage.CategoryService;
import org.alice.bookshop.service.admin.manage.PublisherService;
import org.alice.bookshop.service.common.ulti.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("amBookController")
@RequestMapping("/admin/manage/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private PublisherService publisherService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PaginationService paginator;

	@GetMapping
	public String show(Model model, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "15") int psize) {

		// get Book page
		List<Book> books = bookService.getBooks(p, psize);
		model.addAttribute("books", books);

		// pagination
		long totalPage = bookService.getTotalPage(psize);
		List<Integer> pageList = paginator.getPageList();
		model.addAttribute("pages", pageList);

		// current active page
		model.addAttribute("curPage", p);
		model.addAttribute("lastPage", totalPage);

		return "/admin/manage/books/show";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("authors", authorService.authorJpa.findAll());
		model.addAttribute("categories", categoryService.categoryJpa.findAll());
		model.addAttribute("publishers", publisherService.publisherJpa.findAll());
		return "/admin/manage/books/add";
	}

	@PostMapping("/add")
	public String add(RedirectAttributes redirAttr, Book book, @RequestParam(required = false, defaultValue = "15") int psize) {
		long totalPage = bookService.getTotalPage(psize);
		String msg = bookService.add(book);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/books?p=" + totalPage;
		} else {
			return "redirect:add";
		}
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable int id) {
		Book book = bookService.bookJpa.getOne(id);
		model.addAttribute("book", book);
		model.addAttribute("authors", authorService.authorJpa.findAll());
		model.addAttribute("categories", categoryService.categoryJpa.findAll());
		model.addAttribute("publishers", publisherService.publisherJpa.findAll());
		return "/admin/manage/books/edit";
	}

	@PostMapping("/{id}/edit")
	public String edit(RedirectAttributes redirAttr, Book book,
			@RequestParam(required = false, defaultValue = "1") int p) {
		String msg = bookService.edit(book);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/books?p=" + p;
		} else {
			return "redirect:edit";
		}
	}

}
