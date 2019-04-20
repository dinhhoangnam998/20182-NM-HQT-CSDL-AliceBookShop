package org.alice.bookshop.controller.admin.statistic;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.alice.bookshop.model.Order;
import org.alice.bookshop.service.admin.statistic.ReceiptService;
import org.alice.bookshop.service.utility.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("asReceiptController")
public class ReceiptController {

	@Autowired
	private ReceiptService receiptService;

	@Autowired
	private PaginationService pagi;
	private Date today = new Date();
	private Date begin = today;
	private Date end = today;

	@GetMapping("/admin/statistic/receipt")
	public String show(Model model, HttpSession ss, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "10") int psize,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {

		if (begin != null) {
			this.begin = begin;
		}
		if (end != null) {
			this.end = end;
		}
		pagi.validate(ss, p, psize);
		Page<Order> orders = receiptService.getReceipts(this.begin, this.end, pagi.getRequestPage(),
				pagi.getPageSize());
		model.addAttribute("orders", orders);

		List<Integer> pageList = pagi.getPageList(orders.getTotalPages());
		model.addAttribute("pages", pageList);

		model.addAttribute("begin", this.begin);
		model.addAttribute("end", this.end);

		long numberOfOrder = receiptService.getTotalNumberOfOrder(this.begin, this.end);
		model.addAttribute("totalNumber", numberOfOrder);
		long numberOfSuccessedOrder = receiptService.getNumberOfSuccessed(this.begin, this.end);
		model.addAttribute("successed", numberOfSuccessedOrder);
		long numberOfCanceledOrder = receiptService.getNumberOfCanceled(this.begin, this.end);
		model.addAttribute("canceled", numberOfCanceledOrder);
		long numberOfDeleveringOrder = receiptService.getNumberOfDelevering(this.begin, this.end);
		model.addAttribute("delevering", numberOfDeleveringOrder);
		long numberOfNewOrder = receiptService.getNumberOfNewOrder(this.begin, this.end);
		model.addAttribute("newOrder", numberOfNewOrder);
		long receipt = receiptService.getReceipt(this.begin, this.end);
		model.addAttribute("receipt", receipt);
		return "/admin/statistic/receipt";
	}
}
