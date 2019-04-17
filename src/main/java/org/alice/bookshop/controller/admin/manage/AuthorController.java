package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.Author;
import org.alice.bookshop.service.admin.manage.AuthorService;
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
		pagi.validate(ss, p, psize);
		Page<Author> authors = authorService.getAuthors(pagi.getRequestPage(), pagi.getPageSize());
		model.addAttribute("authors", authors.getContent());
		List<Integer> pageList = pagi.getPageList(authors.getTotalPages());
		model.addAttribute("pages", pageList);
		return "/admin/manage/authors/show";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("author", new Author());
		return "/admin/manage/authors/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public String add(RedirectAttributes redirAttr, Author author, @RequestParam MultipartFile file) {
		String msg = authorService.add(author, file);
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

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public String edit(RedirectAttributes redirAttr, Author author, @RequestParam MultipartFile file) {
		String msg = authorService.edit(author, file);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/authors?p=" + pagi.getRequestPage();
		} else {
			return "redirect:edit";
		}
	}

	@GetMapping("/{id}/delete")
	public String delete(RedirectAttributes redirAttr, @PathVariable int id) {
		Author author = authorService.authorJpa.getOne(id);
		author.setDeleted(true);
		authorService.authorJpa.save(author);
		redirAttr.addFlashAttribute("msg", "delete author " + author.getName() + " success!");
		return "redirect:/admin/manage/authors?p=" + pagi.getRequestPage();
	}

}
