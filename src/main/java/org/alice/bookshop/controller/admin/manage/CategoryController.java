package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.Category;
import org.alice.bookshop.service.admin.manage.CategoryService;
import org.alice.bookshop.service.utility.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("amCategoryController")
@RequestMapping("/admin/manage/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PaginationService pagi;

	@GetMapping
	public String show(Model model, HttpSession ss, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "15") int psize) {
		pagi.validate(ss, p, psize);
		Page<Category> categories = categoryService.getCategories(pagi.getRequestPage(), pagi.getPageSize());
		model.addAttribute("categories", categories.getContent());
		List<Integer> pageList = pagi.getPageList(categories.getTotalPages());
		model.addAttribute("pages", pageList);
		return "/admin/manage/categories/show";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("category", new Category());
		return "/admin/manage/categories/add";
	}

	@PostMapping("/add")
	public String add(RedirectAttributes redirAttr, Category category) {
		String msg = categoryService.add(category);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/categories?p=" + pagi.getLastPage();
		} else {
			return "redirect:add";
		}
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable int id) {
		Category category = categoryService.categoryJpa.getOne(id);
		model.addAttribute("category", category);
		return "/admin/manage/categories/edit";
	}

	@PostMapping("/{id}/edit")
	public String edit(RedirectAttributes redirAttr, Category category) {
		String msg = categoryService.edit(category);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/categories?p=" + pagi.getRequestPage();
		} else {
			return "redirect:edit";
		}
	}

	@GetMapping("/{id}/delete")
	public String delete(RedirectAttributes redirAttr, @PathVariable int id) {
		Category category = categoryService.categoryJpa.getOne(id);
		category.setDeleted(true);
		categoryService.categoryJpa.save(category);
		redirAttr.addFlashAttribute("msg", "delete category " + category.getName() + " success!");
		return "redirect:/admin/manage/categories?p=" + pagi.getRequestPage();
	}
}
