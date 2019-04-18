package org.alice.bookshop.controller.user.shopping;

import java.util.List;

import org.alice.bookshop.model.Order;
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
		List<Order> orders = shSvc.getHistoryShopping(uid);
		model.addAttribute("orders", orders);
		return "/user/shopping/history";
	}
}
