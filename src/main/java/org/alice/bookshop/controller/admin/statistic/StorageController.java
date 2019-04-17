package org.alice.bookshop.controller.admin.statistic;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.service.admin.statistic.StorageService;
import org.alice.bookshop.service.utility.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("asBookController")
@RequestMapping("/admin/statistic/storage")
public class StorageController {

	@Autowired
	private StorageService storageService;

	@Autowired
	private PaginationService pagi;

	@GetMapping
	public String show(Model model, HttpSession ss, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "15") int psize,
			@RequestParam(required = false, defaultValue = "50") int limit) {
		pagi.validate(ss, p, psize);
		Page<Book> books = storageService.getBookRemainUnderLimit(limit, pagi.getRequestPage(), pagi.getPageSize());
		model.addAttribute("books", books.getContent());
		List<Integer> pageList = pagi.getPageList(books.getTotalPages());
		model.addAttribute("pages", pageList);
		return "/admin/statistic/storage";
	}
}
