package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.Author;
import org.alice.bookshop.service.admin.manage.AuthorService;
import org.alice.bookshop.service.utility.PaginationService;
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
	private PaginationService pagi;

	@GetMapping
	public String show(Model model, HttpSession ss, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "15") int psize) {

		pagi.process(ss, p, psize, authorService.authorJpa.count());
		List<Integer> pageList = pagi.getPageList();
		model.addAttribute("pages", pageList);

		List<Author> authors = authorService.getAuthors(p, pagi.getPageSize());
		model.addAttribute("authors", authors);

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
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/authors?p=" + pagi.getLastPage();
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
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/authors?p=" + pagi.getCurPage();
		} else {
			return "redirect:edit";
		}
	}

	@GetMapping("/{id}/delete")
	public String delete(RedirectAttributes redirAttr, @PathVariable int id) {
		authorService.authorJpa.getOne(id).setDeleted(true);
		return "redirect:/admin/manage/authors";
	}
}
