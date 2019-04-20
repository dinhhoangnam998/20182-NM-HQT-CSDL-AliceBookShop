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

	private int limit = 25;

	@GetMapping
	public String show(Model model, HttpSession ss, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "10") int psize,
			@RequestParam(required = false) Integer limit) {
		if (limit != null) {
			this.limit = limit;
		}
		pagi.validate(ss, p, psize);
		ss.setAttribute("limit", limit);
		Page<Book> books = storageService.getBookRemainUnderLimit(this.limit, pagi.getRequestPage(),
				pagi.getPageSize());
		model.addAttribute("books", books.getContent());
		List<Integer> pageList = pagi.getPageList(books.getTotalPages());
		model.addAttribute("pages", pageList);
		model.addAttribute("limit", this.limit);
		return "/admin/statistic/storage";
	}
}
