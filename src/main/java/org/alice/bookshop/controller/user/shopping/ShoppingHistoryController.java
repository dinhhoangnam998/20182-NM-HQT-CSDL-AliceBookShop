package org.alice.bookshop.controller.user.shopping;

import org.alice.bookshop.service.user.shopping.ShoppingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ShoppingHistoryController {

	@Autowired
	public ShoppingHistoryService shSvc;

	@GetMapping("/{uid}/shopping-history")
	public String showShoppingHistory(Model model, @PathVariable int uid) {

		return "/user/shopping/history";
	}
}
