package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.Book_Sale;
import org.alice.bookshop.model.Sale;
import org.alice.bookshop.service.admin.manage.BookService;
import org.alice.bookshop.service.admin.manage.SaleService;
import org.alice.bookshop.service.utility.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("amSaleController")
@RequestMapping("/admin/manage/sales")
public class SaleController {

	@Autowired
	private SaleService saleService;

	@Autowired
	private BookService bookService;

	@Autowired
	private PaginationService pagi;

	@GetMapping
	public String show(Model model, HttpSession ss, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "15") int psize) {

		pagi.process(ss, p, psize, saleService.saleJpa.count());

		// get sale page
		List<Sale> sales = saleService.getSales(p, pagi.getPageSize());
		model.addAttribute("sales", sales);

		// pagination
		List<Integer> pageList = pagi.getPageList();
		model.addAttribute("pages", pageList);

		return "/admin/manage/sales/show";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("sale", new Sale());
		model.addAttribute("books", bookService.bookJpa.findAll());
		return "/admin/manage/sales/add";
	}

	@RequestMapping(value = "/add", params = { "addRow" })
	public String addRow(final Sale sale, final BindingResult bindingResult, Model model) {
		sale.getBook_sales().add(new Book_Sale());
		model.addAttribute("books", bookService.bookJpa.findAll());
		return "/admin/manage/sales/add";
	}

	@RequestMapping(value = "/add", params = { "removeRow" })
	public String removeRow(final Sale sale, final BindingResult bindingResult, final HttpServletRequest req,
			Model model) {
		final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
		sale.getBook_sales().remove(rowId.intValue());
		model.addAttribute("books", bookService.bookJpa.findAll());
		return "/admin/manage/sales/add";
	}

	@PostMapping("/add")
	public String add(RedirectAttributes redirAttr, Sale sale) {
		String msg = saleService.add(sale);

		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/sales?p=" + pagi.getLastPage();
		} else {
			return "redirect:add";
		}
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable int id) {
		Sale sale = saleService.saleJpa.getOne(id);
		model.addAttribute("sale", sale);
		model.addAttribute("books", bookService.bookJpa.findAll());
		return "/admin/manage/sales/edit";
	}

	@RequestMapping(value = "/{id}/edit", params = { "addRow" })
	public String addRowEdit(final Sale sale, final BindingResult bindingResult, Model model) {
		sale.getBook_sales().add(new Book_Sale());
		model.addAttribute("books", bookService.bookJpa.findAll());
		return "/admin/manage/sales/edit";
	}

	@RequestMapping(value = "/{id}/edit", params = { "removeRow" })
	public String removeRowEdit(final Sale sale, final BindingResult bindingResult, final HttpServletRequest req,
			Model model) {
		final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
		Book_Sale bi = sale.getBook_sales().get(rowId.intValue());
		saleService.bsService.book_saleJpa.delete(bi);
		sale.getBook_sales().remove(rowId.intValue());
		model.addAttribute("books", bookService.bookJpa.findAll());
		return "/admin/manage/sales/edit";
	}

	@PostMapping("/{id}/edit")
	public String edit(RedirectAttributes redirAttr, Sale sale) {
		String msg = saleService.edit(sale);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/sales?p=" + pagi.getCurPage();
		} else {
			return "redirect:edit";
		}
	}

}
