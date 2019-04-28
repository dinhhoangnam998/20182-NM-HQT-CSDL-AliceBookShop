package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.service.admin.manage.AuthorService;
import org.alice.bookshop.service.admin.manage.BookService;
import org.alice.bookshop.service.admin.manage.CategoryService;
import org.alice.bookshop.service.admin.manage.PublisherService;
import org.alice.bookshop.service.utility.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("amBookController")
@RequestMapping("/admin/manage/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PublisherService publisherService;

	@Autowired
	private PaginationService pagi;

	@GetMapping
	public String show(Model model, HttpSession ss, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "15") int psize) {

		pagi.validate(ss, p, psize);

		Page<Book> books = bookService.getBooks(pagi.getRequestPage(), pagi.getPageSize());
		model.addAttribute("books", books.getContent());

		List<Integer> pageList = pagi.getPageList(books.getTotalPages());
		model.addAttribute("pages", pageList);

		return "/admin/manage/books/show";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("authors", authorService.authorJpa.findByDeleted(false));
		model.addAttribute("categories", categoryService.categoryJpa.findByDeleted(false));
		model.addAttribute("publishers", publisherService.publisherJpa.findByDeleted(false));
		model.addAttribute("books", bookService.bookJpa.findByDeleted(false));
		return "/admin/manage/books/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public String add(RedirectAttributes redirAttr, Book book, @RequestParam MultipartFile file,
			@RequestParam MultipartFile[] files, @RequestParam MultipartFile[] thumbs,
			@RequestParam(required = false) Integer[] relateBookIds) {
		String msg = bookService.add(book, file, files, thumbs, relateBookIds);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/books?p=" + pagi.getLastPage();
		} else {
			return "redirect:add";
		}
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable int id) {
		Book book = bookService.bookJpa.getOne(id);
		model.addAttribute("book", book);
		model.addAttribute("authors", authorService.authorJpa.findByDeleted(false));
		model.addAttribute("categories", categoryService.categoryJpa.findByDeleted(false));
		model.addAttribute("publishers", publisherService.publisherJpa.findByDeleted(false));
		model.addAttribute("books", bookService.bookJpa.findByDeleted(false));
		return "/admin/manage/books/edit";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public String edit(RedirectAttributes redirAttr, Book book, @RequestParam MultipartFile file,
			@RequestParam MultipartFile[] files, @RequestParam MultipartFile[] thumbs,
			@RequestParam(required = false) Integer[] relateBookIds) {
		String msg = bookService.edit(book, file, files, thumbs, relateBookIds);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/books?p=" + pagi.getRequestPage();
		} else {
			return "redirect:edit";
		}
	}

	@GetMapping("/{id}/delete")
	public String delete(RedirectAttributes redirAttr, @PathVariable int id) {
		Book book = bookService.bookJpa.getOne(id);
		book.setDeleted(true);
		bookService.bookJpa.save(book);
		redirAttr.addFlashAttribute("msg", "delete book " + book.getName() + " success!");
		return "redirect:/admin/manage/books?p=" + pagi.getRequestPage();
	}
}
