package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Order;
import org.alice.bookshop.service.admin.manage.OrderService;
import org.alice.bookshop.service.common.ulti.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("amOrderController")
@RequestMapping("/admin/manage/orders")
public class OrderController {

	final int CANCELED = -1;
	final int ORDERING = 0;
	final int ORDERED = 1;
	final int DELIVERING = 2;
	final int SUCCESSED = 3;

	@Autowired
	private OrderService orderService;

	@Autowired
	private PaginationService paginator;

	@GetMapping
	public String show(Model model, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "15") int psize) {

		// get order page
		List<Order> orders = orderService.getOrders(p, psize);
		model.addAttribute("orders", orders);

		// pagination
		long totalPage = orderService.getTotalPage(psize);
		List<Integer> pageList = paginator.getPageList(totalPage, p, psize);
		model.addAttribute("pages", pageList);

		// current active page
		model.addAttribute("curPage", p);
		model.addAttribute("lastPage", totalPage);

		return "/admin/manage/orders/show";
	}

	@PostMapping
	public String changeOrderState(@PathVariable int id, @RequestParam int state,
			@RequestParam(required = false, defaultValue = "1") int p) {
		Order order = orderService.orderJpa.getOne(id);
		String msg;

		switch (state) {
		case CANCELED:
			order.setState(CANCELED);
			msg = "CANCELED the Order";
			break;

		case DELIVERING:
			order.setState(DELIVERING);
			msg = "CANCELED the Order";
			break;

		case SUCCESSED:
			order.setState(SUCCESSED);
			break;

		default:
			msg = "Nothing happend";
			break;
		}
		orderService.orderJpa.save(order);

		
		return "redirect:/admin/manage/orders?p=" + p;
	}

}
