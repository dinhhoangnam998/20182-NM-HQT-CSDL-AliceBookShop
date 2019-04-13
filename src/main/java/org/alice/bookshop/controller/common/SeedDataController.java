package org.alice.bookshop.controller.common;

import org.alice.bookshop.model.Order;
import org.alice.bookshop.model.OrderLine;
import org.alice.bookshop.model.User;
import org.alice.bookshop.repository.AuthorJpa;
import org.alice.bookshop.repository.BookJpa;
import org.alice.bookshop.repository.Book_InputJpa;
import org.alice.bookshop.repository.Book_SaleJpa;
import org.alice.bookshop.repository.CategoryJpa;
import org.alice.bookshop.repository.InputJpa;
import org.alice.bookshop.repository.OrderJpa;
import org.alice.bookshop.repository.OrderLineJpa;
import org.alice.bookshop.repository.PublisherJpa;
import org.alice.bookshop.repository.SaleJpa;
import org.alice.bookshop.repository.UserJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SeedDataController {

	@Autowired
	private AuthorJpa aJpa;

	@Autowired
	private CategoryJpa cJpa;

	@Autowired
	private PublisherJpa pJpa;

	@Autowired
	private BookJpa bJpa;

	@Autowired
	private InputJpa iJpa;

	@Autowired
	private SaleJpa sJpa;

	@Autowired
	private Book_InputJpa b_iJpa;

	@Autowired
	private Book_SaleJpa b_sJpa;

	@Autowired
	private UserJpa uJpa;

	@Autowired
	private OrderJpa orderJpa;

	@Autowired
	private OrderLineJpa orderDetailJpa;

	@GetMapping("/test-hibernate")
	public String test() {
		User user = uJpa.getOne(1);
		user.setName("just for test hibernate");
		Order order = new Order();
		order.getUser().setId(1);
		order.setNote("test hibernate");
		orderJpa.save(order);
		return "redirect:/admin/manage/authors";
	}

	@SuppressWarnings("deprecation")
	@GetMapping("/seed-data")
	public String seedData() {
		for (int i = 1; i <= 200; i++) {
//			Author author = new Author();
//			author.setName("author " + i);
//			author.setBirthday(new Date(2019 - 40 - 1900 + (int) Math.floor(Math.random() * 20),
//					(int) Math.floor(Math.random() * 12), (int) Math.floor(Math.random() * 28)));
//			author.setImgURL("/img/author/...");
//			aJpa.save(author);
//
//			Category category = new Category();
//			category.setName("category " + i);
//			cJpa.save(category);
//
//			Publisher publisher = new Publisher();
//			publisher.setName("publisher " + i);
//			publisher.setAddress("city " + i);
//			publisher.setPhone("0123456789");
//			pJpa.save(publisher);
//
//			Book book = new Book();
//			book.setAuthor(author);
//			book.setCategory(category);
//			book.setPublisher(publisher);
//			book.setName("book " + i);
//			book.setHeight(30);
//			book.setWidth(25);
//			book.setDescription("description " + i);
//			book.setTotalPage(50 + i);
//			bJpa.save(book);
//
//			Input input = new Input();
//			input.setInputDate(new Date());
//			input.setNote("input event " + i);
//			iJpa.save(input);
//
//			Sale sale = new Sale();
//			sale.setBeginDate(new Date());
//			sale.setEndDate(new Date(119, 6, 15));
//			sale.setName("sale event " + i);
//			sJpa.save(sale);
//
//			Book_Input b_i = new Book_Input();
//			b_i.setBook(book);
//			b_i.setInput(input);
//			b_i.setQuantity(i);
//			b_i.setCoverPrice(20 + i * 10);
//			b_i.setInputPrice(b_i.getCoverPrice() * 90 / 100);
//			b_iJpa.save(b_i);
//
//			Book_Sale b_s = new Book_Sale();
//			b_s.setBook(book);
//			b_s.setSale(sale);
//			b_s.setPercent(15);
//			b_sJpa.save(b_s);
//
//			User user = new User();
//			user.setUsername("username" + i);
//			user.setPassword("password");
//			user.setEmail("email" + i + "@gmail.com");
//			user.setName("name " + i);
//			user.setAddress("city " + i);
//			uJpa.save(user);
//
//			Order order = new Order();
//			order.setOrderDate(new Date(115 + (int) Math.floor(Math.random() * 5), (int) Math.floor(Math.random() * 12),
//					(int) Math.floor(Math.random() * 28)));
//			order.setUser(user);
//			order.setNote("delivery to " + user.getAddress());
//			order.setState(1);
//			orderJpa.save(order);

			for (int j = 1; j <= 6; j++) {
				OrderLine orderDetail = new OrderLine();
				orderDetail.setOrder(orderJpa.getOne(i));
				orderDetail.setBook(bJpa.getOne(1 + (int) Math.floor(Math.random() * 190)));
				orderDetail.setQuantity((int) Math.floor(Math.random() * 10));
				orderDetailJpa.save(orderDetail);
			}

		}
		return "redirect:/admin/manage/authors";
	}
}
