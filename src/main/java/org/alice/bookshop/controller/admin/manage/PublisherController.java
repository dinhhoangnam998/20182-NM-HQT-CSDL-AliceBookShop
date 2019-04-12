package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Publisher;
import org.alice.bookshop.service.admin.manage.PublisherService;
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

@Controller("amPublisherController")
@RequestMapping("/admin/manage/publishers")
public class PublisherController {
	@Autowired
	private PublisherService publisherService;

	@Autowired
	private PaginationService paginator;

	@GetMapping
	public String show(Model model, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "15") int psize) {

		// get publisher page
		List<Publisher> publishers = publisherService.getPublishers(p, psize);
		model.addAttribute("publishers", publishers);

		// pagination
		long totalPage = publisherService.getTotalPage(psize);
		List<Integer> pageList = paginator.getPageList(totalPage, p, psize);
		model.addAttribute("pages", pageList);
		
		// current active page
		model.addAttribute("curPage", p);
		model.addAttribute("lastPage", totalPage);

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
		if (msg.equals("Add publisher successed")) {
			return "redirect:/admin/manage/publishers";
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
		if (msg.equals("Edit publisher successed")) {
			return "redirect:/admin/manage/publishers";
		} else {
			return "redirect:edit";
		}
	}
}
