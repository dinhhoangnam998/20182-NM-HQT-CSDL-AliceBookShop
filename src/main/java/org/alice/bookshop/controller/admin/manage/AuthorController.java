package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Author;
import org.alice.bookshop.service.admin.manage.AuthorService;
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

@Controller("amAuthorController")
@RequestMapping("/admin/manage/authors")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@Autowired
	private PaginationService paginator;

	@GetMapping
	public String show(Model model, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "15") int psize) {

		// get author page
		List<Author> authors = authorService.getAuthors(p, psize);
		model.addAttribute("authors", authors);

		// pagination
		long totalPage = authorService.getTotalPage(psize);
		List<Integer> pageList = paginator.getPageList(totalPage, p, psize);
		model.addAttribute("pages", pageList);
		
		// current active page
		model.addAttribute("curPage", p);
		model.addAttribute("lastPage", totalPage);

		return "/admin/manage/authors/show";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("author", new Author());
		return "/admin/manage/authors/add";
	}

	@PostMapping("/add")
	public String add(RedirectAttributes redirAttr, Author author) {
		String msg = authorService.add(author);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.equals("Add author successed")) {
			return "redirect:/admin/manage/authors";
		} else {
			return "redirect:add";
		}
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable int id) {
		Author author = authorService.authorJpa.getOne(id);
		model.addAttribute("author", author);
		return "/admin/manage/authors/edit";
	}

	@PostMapping("/{id}/edit")
	public String edit(RedirectAttributes redirAttr, Author author) {
		String msg = authorService.edit(author);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.equals("Edit author successed")) {
			return "redirect:/admin/manage/authors";
		} else {
			return "redirect:edit";
		}
	}
}
