package org.alice.bookshop.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Bill {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private Date billDate;
	private String note;
	private int state;
	
	@ManyToOne
	private User user;
}
