package org.alice.bookshop.controller.user.shopping;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.Author;
import org.alice.bookshop.service.user.shopping.NavigateService;
import org.alice.bookshop.service.utility.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("usNavigateController")
public class NavigateController {

	@Autowired
	private NavigateService navigateService;
	
	@Autowired
	private PaginationService pagi;
	
	@GetMapping("/authors")
	public String showAuthors(Model model, HttpSession ss, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "12") int psize) {
		pagi.validate(ss, p, psize);
		Page<Author> authors = navigateService.getAuthors(pagi.getRequestPage(), pagi.getPageSize());
		List<List<Author>> authorRows = navigateService.getAuthorRows(authors.getContent());
		model.addAttribute("rows", authorRows);
		List<Integer> pageList = pagi.getPageList(authors.getTotalPages());
		model.addAttribute("pages", pageList);
		
		return "/user/shopping/navigate/author";
	}
}
