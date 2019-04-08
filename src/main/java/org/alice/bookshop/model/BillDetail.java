package org.alice.bookshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class BillDetail {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int quantity;
	
	@ManyToOne
	private Bill bill;
	
	@OneToOne
	private Book book;
	
}
