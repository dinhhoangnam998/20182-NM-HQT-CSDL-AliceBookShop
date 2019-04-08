package org.alice.bookshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Book {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private int width;
	private int height;
	private int pagenumber;
	private int year;
	private int coverprice;
	private int inputprice;
	private String description;
	private String imageurl;
	
	@ManyToOne
	private Author author;
	
	@ManyToOne
	private Category category;
	
	@ManyToOne
	private Publisher publisher;
}
