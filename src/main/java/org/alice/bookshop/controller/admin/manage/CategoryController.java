package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Category;
import org.alice.bookshop.service.admin.manage.CategoryService;
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

@Controller("amCategoryController")
@RequestMapping("/admin/manage/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PaginationService paginator;

	@GetMapping
	public String show(Model model, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "15") int psize) {

		// get category page
		List<Category> categories = categoryService.getCategories(p, psize);
		model.addAttribute("categories", categories);

		// pagination
		long totalPage = categoryService.getTotalPage(psize);
		List<Integer> pageList = paginator.getPageList();
		model.addAttribute("pages", pageList);

		// current active page
		model.addAttribute("curPage", p);
		model.addAttribute("lastPage", totalPage);

		return "/admin/manage/categories/show";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("category", new Category());
		return "/admin/manage/categories/add";
	}

	@PostMapping("/add")
	public String add(RedirectAttributes redirAttr, Category category,
			@RequestParam(required = false, defaultValue = "15") int psize) {
		long totalPage = categoryService.getTotalPage(psize);
		String msg = categoryService.add(category);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/categories?p=" + totalPage;
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
	public String edit(RedirectAttributes redirAttr, Category category,
			@RequestParam(required = false, defaultValue = "1") int p) {
		String msg = categoryService.edit(category);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/categories?p=" + p;
		} else {
			return "redirect:edit";
		}
	}
}
