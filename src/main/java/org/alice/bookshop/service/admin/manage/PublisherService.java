package org.alice.bookshop.service.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Publisher;
import org.alice.bookshop.repository.PublisherJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("amPublisherService")
public class PublisherService {
	@Autowired
	public PublisherJpa publisherJpa;

	private boolean isPublisherExit(Publisher Publisher) {
		Publisher isExit = publisherJpa.findByName(Publisher.getName());
		return (isExit != null);
	}

	public List<Publisher> getPublishers(int p, int psize) {
		Pageable pageable = PageRequest.of(p - 1, psize);
		Page<Publisher> Publishers = publisherJpa.findAll(pageable);
		return Publishers.getContent();
	}

	public String add(Publisher Publisher) {
		if (isPublisherExit(Publisher)) {
			return "publisher already exit";
		} else {
			publisherJpa.save(Publisher);
			return "Add publisher successed";
		}
	}

	public String edit(Publisher Publisher) {
		if (isPublisherExit(Publisher)) {
			return "publisher already exit";
		} else {
			publisherJpa.save(Publisher);
			return "Edit publisher successed";
		}
	}

	public long getTotalPage(int psize) {
		long total = publisherJpa.count();
		long totalPage = total / psize;
		if (total % psize != 0) {
			totalPage += 1;
		}
		return totalPage;
	}
}
