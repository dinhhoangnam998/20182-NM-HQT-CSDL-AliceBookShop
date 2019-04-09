package org.alice.bookshop.controller.admin.manage;

import org.alice.bookshop.service.admin.manage.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("amAuthorController") @RequestMapping("/admin/manage/authors")
public class AuthorController {

	@Autowired
	private AuthorService authorService;
	
	@GetMapping
	public String show(Model model) {
		
		return "/admin/manage/authors/show";
	}
	
}
