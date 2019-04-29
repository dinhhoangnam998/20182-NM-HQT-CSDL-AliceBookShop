package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.Book_Input;
import org.alice.bookshop.model.Input;
import org.alice.bookshop.repository.SupplierJpa;
import org.alice.bookshop.service.admin.manage.BookService;
import org.alice.bookshop.service.admin.manage.InputService;
import org.alice.bookshop.service.utility.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("amInputController")
@RequestMapping("/admin/manage/inputs")
public class InputController {

	@Autowired
	private InputService inputService;

	@Autowired
	private BookService bookService;

	@Autowired
	private PaginationService pagi;
	
	@Autowired
	private SupplierJpa supplierJpa;

	@GetMapping
	public String show(Model model, HttpSession ss, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "15") int psize) {
		pagi.validate(ss, p, psize);
		Page<Input> inputs = inputService.getInputs(pagi.getRequestPage(), pagi.getPageSize());
		model.addAttribute("inputs", inputs.getContent());
		List<Integer> pageList = pagi.getPageList(inputs.getTotalPages());
		model.addAttribute("pages", pageList);
		return "/admin/manage/inputs/show";
	}

	@GetMapping("/add")
	public String add(Model model) {
		Input input = new Input();
		model.addAttribute("input", input);
		model.addAttribute("books", bookService.bookJpa.findAll());
		model.addAttribute("suppliers", supplierJpa.findByDeleted(false));
		return "/admin/manage/inputs/add";
	}

	@RequestMapping(value = "/add", params = { "addRow" })
	public String addRow(final Input input, final BindingResult bindingResult, Model model) {
		input.getBook_inputs().add(new Book_Input());
		model.addAttribute("books", bookService.bookJpa.findAll());
		model.addAttribute("suppliers", supplierJpa.findByDeleted(false));
		return "/admin/manage/inputs/add";
	}

	@RequestMapping(value = "/add", params = { "removeRow" })
	public String removeRow(final Input input, final BindingResult bindingResult, final HttpServletRequest req,
			Model model) {
		final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
		input.getBook_inputs().remove(rowId.intValue());
		model.addAttribute("books", bookService.bookJpa.findAll());
		model.addAttribute("suppliers", supplierJpa.findByDeleted(false));
		return "/admin/manage/inputs/add";
	}

	@PostMapping("/add")
	public String add(RedirectAttributes redirAttr, Input input) {
		String msg = inputService.add(input);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/inputs?p=" + pagi.getLastPage();
		} else {
			return "redirect:add";
		}
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable int id) {
		Input input = inputService.inputJpa.getOne(id);
		model.addAttribute("input", input);
		model.addAttribute("books", bookService.bookJpa.findAll());
		model.addAttribute("suppliers", supplierJpa.findByDeleted(false));
		return "/admin/manage/inputs/edit";
	}

	@RequestMapping(value = "/{id}/edit", params = { "addRow" })
	public String addRowEdit(final Input input, final BindingResult bindingResult, Model model) {
		input.getBook_inputs().add(new Book_Input());
		model.addAttribute("books", bookService.bookJpa.findAll());
		model.addAttribute("suppliers", supplierJpa.findByDeleted(false));
		return "/admin/manage/inputs/edit";
	}

	@RequestMapping(value = "/{id}/edit", params = { "removeRow" })
	public String removeRowEdit(final Input input, final BindingResult bindingResult, final HttpServletRequest req,
			Model model) {
		final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
		Book_Input bi = input.getBook_inputs().get(rowId.intValue());
		inputService.biService.book_inputJpa.delete(bi);
		input.getBook_inputs().remove(rowId.intValue());
		model.addAttribute("books", bookService.bookJpa.findAll());
		model.addAttribute("suppliers", supplierJpa.findByDeleted(false));
		return "/admin/manage/inputs/edit";
	}

	@PostMapping("/{id}/edit")
	public String edit(RedirectAttributes redirAttr, Input input) {
		String msg = inputService.edit(input);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/inputs?p=" + pagi.getRequestPage();
		} else {
			return "redirect:edit";
		}
	}

	@GetMapping("/{id}/delete")
	public String delete(RedirectAttributes redirAttr, @PathVariable int id) {
		Input input = inputService.inputJpa.getOne(id);
		input.setDeleted(true);
		inputService.inputJpa.save(input);
		redirAttr.addFlashAttribute("msg", "delete input id: " + input.getId() + " success!");
		return "redirect:/admin/manage/inputs?p=" + pagi.getRequestPage();
	}

}
