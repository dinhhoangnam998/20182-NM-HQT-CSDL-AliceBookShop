package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import org.alice.bookshop.model.User;
import org.alice.bookshop.service.admin.manage.UserService;
import org.alice.bookshop.service.common.ulti.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("amUserController") @RequestMapping("/admin/manage/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PaginationService paginator;

	@GetMapping
	public String show(Model model, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "15") int psize) {

		// get user page
		List<User> users = userService.getUsers(p, psize);
		model.addAttribute("users", users);

		// pagination
		long totalPage = userService.getTotalPage(psize);
		List<Integer> pageList = paginator.getPageList(totalPage, p, psize);
		model.addAttribute("pages", pageList);
		
		// current active page
		model.addAttribute("curPage", p);
		model.addAttribute("lastPage", totalPage);

		return "/admin/manage/users/show";
	}
	
	@GetMapping("/{id}/block")
	public String block(RedirectAttributes redirAttr, @PathVariable int id) {
		User user = userService.block(id);
		String msg = "Block user id = " + user.getId() + " success!";
		redirAttr.addFlashAttribute("msg", msg);
		return "redirect:/admin/manage/users";
	}
	
	@GetMapping("/{id}/unblock")
	public String unblock(RedirectAttributes redirAttr, @PathVariable int id) {
		User user = userService.unblock(id);
		String msg = "Unblock user id = " + user.getId() + " success!";
		redirAttr.addFlashAttribute("msg", msg);
		return "redirect:/admin/manage/users";
	}
}
