package org.alice.bookshop.model;

import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Proxy(lazy = false)
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int width;
	private int height;
	private int totalPage;
	private int coverPrice;
	private int inputPrice;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date releaseDate;
	private String description;
	private int remainQuantity;
	private String imgURL;
	@ElementCollection
	private List<String> imgURLs;
	@ElementCollection
	private List<String> thumbURLs;

	@ManyToOne
	private Author author = new Author();

	@ManyToOne
	private Category category = new Category();

	@ManyToOne
	private Publisher publisher = new Publisher();

}
