package org.alice.bookshop.controller.user.shopping;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.Order;
import org.alice.bookshop.model.User;
import org.alice.bookshop.service.user.shopping.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller("usCartController")
@RequestMapping("/carts/{id}")
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping
	@ResponseBody
	public Order getCart(Model model, @PathVariable int id) {
		Order order = cartService.orderJpa.getOne(id);
		return order;
	}

	@GetMapping("/add")
	@ResponseBody
	public boolean addToCart(@PathVariable int id, @RequestParam int bid, @RequestParam(defaultValue = "1") int q) {
		return cartService.add(id, bid, q);
	}

	@GetMapping("/remove")
	@ResponseBody
	public boolean removeFromCart(@RequestParam int olid, @RequestParam int bid,
			@RequestParam(defaultValue = "1") int q) {
		return cartService.remove(olid, bid, q);
	}

	@GetMapping("/go-checkout")
	public String checkout(Model model, @PathVariable int id) {
		model.addAttribute("cart", cartService.orderJpa.getOne(id));
		return "/user/shopping/cart/go-check-out";
	}

	@GetMapping("/checkout")
	public String checkout(HttpSession ss, RedirectAttributes redirAttr, @PathVariable int id) {
		List<Integer> invalidBIds = cartService.tryCheckout(id);
		redirAttr.addFlashAttribute("msgs", invalidBIds);
		if (invalidBIds.size() == 0) {
			cartService.makeOrder(id);
			User user = (User) ss.getAttribute("user");
			return "redirect:/" + user.getId() + "/shopping-history";
		} else {
			return "redirect:/checkout";
		}

	}
}
