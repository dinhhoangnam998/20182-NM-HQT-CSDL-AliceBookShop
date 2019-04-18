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
		for (int i = 1; i <= 100; i++) {
			Author author = new Author();
			author.setName("author " + i);
			author.setBirthday(rdD(50, 20));
			author.setImgURL("/images/author/...");
			aJpa.save(author);

			Category category = new Category();
			category.setName("category " + i);
			cJpa.save(category);

			Publisher publisher = new Publisher();
			publisher.setName("publisher " + i);
			publisher.setAddress("city " + i);
			publisher.setPhone("0" + rd(0, 9) + "23" + rd(0, 9) + rd(0, 9) + rd(0, 9) + rd(0, 9) + rd(0, 9) + rd(0, 9));
			pJpa.save(publisher);

			Book book = new Book();
			book.setAuthor(aJpa.getOne(rd(1, i)));
			book.setCategory(cJpa.getOne(rd(1, i)));
			book.setPublisher(pJpa.getOne(rd(1, i)));
			book.setName("book " + i);
			book.setHeight(rd(1, 5) * 10);
			book.setWidth(rd(1, 7) * 10);
			book.setDescription("description " + i);
			book.setTotalPage(rd(30, 200));
			book.setCoverPrice(rd(5, 25) * 10);
			book.setReleaseDate(rdD(5, 5));
			book.setSalePrice(book.getCoverPrice() * 85 / 100);
			bJpa.save(book);

			Input input = new Input();
			input.setInputDate(rdD(5, 5));
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
			user.setBirthday(rdD(30, 10));
			user.setName("name " + i);
			user.setAddress("city " + i);
			uJpa.save(user);
		}

		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword(pwE.encode("password"));
		admin.setName("adminstrator");
		admin.setPrivilege(1);
		uJpa.save(admin);

		return "redirect:/admin/manage/authors";
	}

	@GetMapping("/seed-data-2")
	public String seedData2() {
		for (int i = 1; i <= 100; i++) {

			int quantity1 = rd(3, 6);
			for (int j = 1; j <= quantity1; j++) {
				Book_Input b_i = new Book_Input();
				b_i.setInput(iJpa.getOne(i));
				Book b = bJpa.getOne(rd(2, 90));
				b_i.setBook(b);
				b_i.setQuantity(rd(1, 6) * 10);
				b.setRemainQuantity(b.getRemainQuantity() + b_i.getQuantity());
				bJpa.save(b);
				b_i.setInputPrice(b.getCoverPrice() * 20);
				b_iJpa.save(b_i);

			}

			int quantity2 = rd(3, 6);
			for (int j = 1; j <= quantity2; j++) {
				Book_Sale b_s = new Book_Sale();
				b_s.setSale(sJpa.getOne(i));
				Book book = bJpa.getOne(rd(2, 90));
				b_s.setBook(book);
				b_s.setPercent(rd(10, 40));
				book.setSalePrice(book.getSalePrice() * (100 - b_s.getPercent()) / 100);
				bJpa.save(book);
				b_sJpa.save(b_s);

			}

			Order order = new Order();
			order.setOrderDate(rdD(3, 3));
			order.setUser(uJpa.getOne(rd(2, 50)));
			order.setNote("user note");
			order.setState(rd(0, 4));
			orderJpa.save(order);

			int totalOrderCharge = 0;
			int quantity = rd(2, 5);
			for (int j = 1; j <= quantity; j++) {
				OrderLine orderDetail = new OrderLine();
				orderDetail.setOrder(order);
				Book b = bJpa.getOne(rd(2, 90));

				orderDetail.setBook(b);
				orderDetail.setQuantity(rd(1, 10));
				int totalLine = b.getSalePrice() * orderDetail.getQuantity();
				orderDetail.setTotalLine(totalLine);
				totalOrderCharge += totalLine;
				orderDetailJpa.save(orderDetail);
			}

			order.setTotal(totalOrderCharge);
			orderJpa.save(order);

		}

		return "redirect:/admin/manage/authors";
	}
}
