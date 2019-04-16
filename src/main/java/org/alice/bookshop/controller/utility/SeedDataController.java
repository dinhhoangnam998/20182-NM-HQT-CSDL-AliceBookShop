package org.alice.bookshop.controller.utility;

import java.util.Date;

import org.alice.bookshop.model.Author;
import org.alice.bookshop.model.Book;
import org.alice.bookshop.model.Book_Input;
import org.alice.bookshop.model.Book_Sale;
import org.alice.bookshop.model.Category;
import org.alice.bookshop.model.Input;
import org.alice.bookshop.model.Order;
import org.alice.bookshop.model.OrderLine;
import org.alice.bookshop.model.Publisher;
import org.alice.bookshop.model.Sale;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	private PasswordEncoder pwE;

	public int rd(int min, int max) {
		return min + (int) Math.floor(Math.random() * (max - min + 1));
	}

	@SuppressWarnings("deprecation")
	public Date rdD(int age, int var) {
		return new Date(2019 - age - 1900 + (int) Math.floor(Math.random() * var), (int) Math.floor(Math.random() * 12),
				(int) Math.floor(Math.random() * 28));
	}

	@GetMapping("/seed-data")
	public String seedData() {
		for (int i = 1; i <= 200; i++) {
			Author author = new Author();
			author.setName("author " + i);
			author.setBirthday(rdD(50, 20));
			author.setImgURL("/img/author/...");
			aJpa.save(author);

			Category category = new Category();
			category.setName("category " + i);
			cJpa.save(category);

			Publisher publisher = new Publisher();
			publisher.setName("publisher " + i);
			publisher.setAddress("city " + i);
			publisher.setPhone("0123456789");
			pJpa.save(publisher);

			Book book = new Book();
			book.setAuthor(author);
			book.setCategory(category);
			book.setPublisher(publisher);
			book.setName("book " + i);
			book.setHeight(30);
			book.setWidth(25);
			book.setDescription("description " + i);
			book.setTotalPage(rd(30, 200));
			book.setCoverPrice(rd(50, 250));
//			book.setInputPrice(book.getCoverPrice() * rd(10, 35) / 100);
			bJpa.save(book);

			Input input = new Input();
			input.setInputDate(rdD(5, 4));
			input.setNote("input event " + i);
			iJpa.save(input);

			Sale sale = new Sale();
			sale.setBeginDate(rdD(1, 0));
			sale.setEndDate(rdD(1, 1));
			sale.setName("sale event " + i);
			sJpa.save(sale);

			User user = new User();
			user.setUsername("username" + i);
			user.setPassword(pwE.encode("password"));
			user.setEmail("email" + i + "@gmail.com");
			user.setName("name " + i);
			user.setAddress("city " + i);
			uJpa.save(user);
		}

		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword(pwE.encode("password"));
		admin.setPrivilege(1);
		uJpa.save(admin);

		return "redirect:/admin/manage/authors";
	}

	@GetMapping("/seed-data-2")
	public String seedData2() {
		for (int i = 1; i <= 200; i++) {
			Order order = new Order();
			order.setOrderDate(rdD(3, 3));
			order.setUser(uJpa.getOne(rd(2, 150)));
			order.setNote("user note");
			order.setState(rd(-1, 3));
			orderJpa.save(order);

			int quantity = rd(2, 7);
			for (int j = 1; j <= quantity; j++) {
				OrderLine orderDetail = new OrderLine();
				orderDetail.setOrder(order);
				orderDetail.setBook(bJpa.getOne(rd(2, 190)));
				orderDetail.setQuantity(rd(1, 10));
				orderDetailJpa.save(orderDetail);
			}

			quantity = rd(3, 6);
			for (int j = 1; j <= quantity; j++) {
				Book_Input b_i = new Book_Input();
				b_i.setInput(iJpa.getOne(i));
				b_i.setBook(bJpa.getOne(rd(2, 190)));
				b_i.setQuantity(i);
				b_iJpa.save(b_i);

			}

			quantity = rd(3, 10);
			for (int j = 1; j <= quantity; j++) {
				Book_Sale b_s = new Book_Sale();
				b_s.setSale(sJpa.getOne(i));
				b_s.setBook(bJpa.getOne(rd(2, 190)));
				b_s.setPercent(rd(10, 40));
				b_sJpa.save(b_s);

			}
		}

		return "redirect:/admin/manage/authors";
	}
}
