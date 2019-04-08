package org.alice.bookshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Book_Input {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int quantity;
	
	@OneToOne
	private Book book;
	
	@OneToOne
	private Input input;
}
