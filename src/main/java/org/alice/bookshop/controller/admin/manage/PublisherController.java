package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.Publisher;
import org.alice.bookshop.service.admin.manage.PublisherService;
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

@Controller("amPublisherController")
@RequestMapping("/admin/manage/publishers")
public class PublisherController {

	@Autowired
	private PublisherService publisherService;

	@Autowired
	private PaginationService pagi;

	@GetMapping
	public String show(Model model, HttpSession ss, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "15") int psize) {
		pagi.validate(ss, p, psize);
		Page<Publisher> publishers = publisherService.getPublishers(pagi.getRequestPage(), pagi.getPageSize());
		model.addAttribute("publishers", publishers.getContent());
		List<Integer> pageList = pagi.getPageList(publishers.getTotalPages());
		model.addAttribute("pages", pageList);
		return "/admin/manage/publishers/show";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("publisher", new Publisher());
		return "/admin/manage/publishers/add";
	}

	@PostMapping("/add")
	public String add(RedirectAttributes redirAttr, Publisher publisher) {
		String msg = publisherService.add(publisher);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/publishers?p=" + pagi.getLastPage();
		} else {
			return "redirect:add";
		}
	}

	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable int id) {
		Publisher publisher = publisherService.publisherJpa.getOne(id);
		model.addAttribute("publisher", publisher);
		return "/admin/manage/publishers/edit";
	}

	@PostMapping("/{id}/edit")
	public String edit(RedirectAttributes redirAttr, Publisher publisher) {
		String msg = publisherService.edit(publisher);
		redirAttr.addFlashAttribute("msg", msg);
		if (msg.contains("successed")) {
			return "redirect:/admin/manage/publishers?p=" + pagi.getRequestPage();
		} else {
			return "redirect:edit";
		}
	}

	@GetMapping("/{id}/delete")
	public String delete(RedirectAttributes redirAttr, @PathVariable int id) {
		Publisher publisher = publisherService.publisherJpa.getOne(id);
		publisher.setDeleted(true);
		publisherService.publisherJpa.save(publisher);
		redirAttr.addFlashAttribute("msg", "delete publisher " + publisher.getName() + " success!");
		return "redirect:/admin/manage/publishers?p=" + pagi.getRequestPage();
	}

}
