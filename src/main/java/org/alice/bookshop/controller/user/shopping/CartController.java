package org.alice.bookshop.controller.user.shopping;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.Order;
import org.alice.bookshop.model.User;
import org.alice.bookshop.repository.BookJpa;
import org.alice.bookshop.repository.OrderJpa;
import org.alice.bookshop.service.user.account.AccountService;
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
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private BookJpa bookJpa;

	@Autowired
	private OrderJpa orderJpa;

	@GetMapping
	@ResponseBody
	public Order getCart(HttpSession session, Model model, @PathVariable int id) {
		Order order = cartService.orderJpa.getOne(id);
		return order;
	}

	@GetMapping("/add")
	public String addToCart(HttpSession ss, RedirectAttributes redirAttr, @PathVariable int id, @RequestParam int bid,
			@RequestParam(defaultValue = "1") int q) {
		if (id == -1) {
			redirAttr.addFlashAttribute("warning", "Bạn vui lòng đăng nhập để thêm sách vào giỏ hàng.");
			return "redirect:/books/" + bid;
		}
		redirAttr.addFlashAttribute("result", cartService.add(id, bid, q));
		redirAttr.addFlashAttribute("q", q);
		redirAttr.addFlashAttribute("bookName", bookJpa.getOne(bid).getName());
		return "redirect:/books/" + bid;
	}

	@GetMapping("/remove")
	public String removeFromCart(RedirectAttributes redirAttr, @PathVariable int id, @RequestParam int olid,
			@RequestParam(defaultValue = "1") int q) {
		cartService.remove(olid, q);
		return "redirect:/carts/" + id + "/manage";
	}

	@GetMapping("/manage")
	public String checkout(Model model, @PathVariable int id) {
		Order cart = cartService.orderJpa.getOne(id);
		cartService.updateCharge(cart);
		model.addAttribute("cart", cart);
		return "/user/shopping/cart/manage";
	}

	@GetMapping("/check-out")
	public String checkout(HttpSession ss, RedirectAttributes redirAttr, @PathVariable int id) {
		List<Integer> invalidBIds = cartService.tryCheckout(id);
		redirAttr.addFlashAttribute("msgs", invalidBIds);
		if (invalidBIds.size() == 0) {
			User user = (User) ss.getAttribute("user");
			cartService.makeOrder(id, user);
			Order cart = accountService.getCart(user);
			ss.setAttribute("cart", cart);
			return "redirect:/" + user.getId() + "/shopping-history";
		} else {
			return "redirect:/check-out";
		}

	}
}
