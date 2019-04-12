package org.alice.bookshop.controller.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Bill;
import org.alice.bookshop.service.admin.manage.BillService;
import org.alice.bookshop.service.common.ulti.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("amBillController") @RequestMapping("/admin/manage/bills")
public class BillController {

	@Autowired
	private BillService billService;
	
	@Autowired
	private PaginationService paginator;

	@GetMapping
	public String show(Model model, @RequestParam(required = false, defaultValue = "1") int p,
			@RequestParam(required = false, defaultValue = "15") int psize) {

		// get bill page
		List<Bill> bills = billService.getBills(p, psize);
		model.addAttribute("bills", bills);

		// pagination
		long totalPage = billService.getTotalPage(psize);
		List<Integer> pageList = paginator.getPageList(totalPage, p, psize);
		model.addAttribute("pages", pageList);
		
		// current active page
		model.addAttribute("curPage", p);
		model.addAttribute("lastPage", totalPage);

		return "/admin/manage/bills/show";
	}
	
}
