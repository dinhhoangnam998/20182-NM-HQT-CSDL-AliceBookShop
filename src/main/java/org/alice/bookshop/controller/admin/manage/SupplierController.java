package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.Supplier;
import org.alice.bookshop.service.admin.manage.SupplierService;
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

@Controller("amSupplierController")
@RequestMapping("/admin/manage/suppliers")
public class SupplierController {

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private PaginationService pagi;

	@GetMapping
	public String show(Model model, HttpSession ss, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "15") int psize) {
		pagi.validate(ss, p, psize);
		Page<Supplier> suppliers = supplierService.getSuppliers(pagi.getRequestPage(), pagi.getPageSize());
		model.addAttribute("suppliers", suppliers.getContent());
		List<Integer> pageList = pagi.getPageList(suppliers.getTotalPages());
		model.addAttribute("pages", pageList);
		return "/admin/manage/suppliers/show";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("supplier", new Supplier());
		return "/admin/manage/suppliers/add";
	}

	@PostMapping("/add")
	public String add(RedirectAttributes redirAttr, Supplier supplier) {
		String msg = supplierService.add(supplier);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/suppliers?p=" + pagi.getLastPage();
		} else {
			return "redirect:add";
		}
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable int id) {
		Supplier supplier = supplierService.supplierJpa.getOne(id);
		model.addAttribute("supplier", supplier);
		return "/admin/manage/suppliers/edit";
	}

	@PostMapping("/{id}/edit")
	public String edit(RedirectAttributes redirAttr, Supplier supplier) {
		String msg = supplierService.edit(supplier);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/suppliers?p=" + pagi.getRequestPage();
		} else {
			return "redirect:edit";
		}
	}

	@GetMapping("/{id}/delete")
	public String delete(RedirectAttributes redirAttr, @PathVariable int id) {
		Supplier supplier = supplierService.supplierJpa.getOne(id);
		supplier.setDeleted(true);
		supplierService.supplierJpa.save(supplier);
		redirAttr.addFlashAttribute("msg", "delete supplier " + supplier.getName() + " success!");
		return "redirect:/admin/manage/suppliers?p=" + pagi.getRequestPage();
	}

}
