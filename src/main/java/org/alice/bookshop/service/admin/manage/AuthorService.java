package org.alice.bookshop.service.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Author;
import org.alice.bookshop.repository.AuthorJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("amAuthorService")
public class AuthorService {

	@Autowired
	public AuthorJpa authorJpa;

	private boolean isAuthorExit(Author author) {
		Author isExit = authorJpa.findByName(author.getName());
		return (isExit != null);
	}

	public List<Author> getAuthors(int p, int psize) {
		Pageable pageable = PageRequest.of(p - 1, psize);
		Page<Author> authors = authorJpa.findAll(pageable);
		return authors.getContent();
	}

	public String add(Author author) {
		if (isAuthorExit(author)) {
			return "Author already exit";
		} else {
			authorJpa.save(author);
			return "Add author successed";
		}
	}

	public String edit(Author author) {
		if (isAuthorExit(author)) {
			return "Author already exit";
		} else {
			authorJpa.save(author);
			return "Edit author successed";
		}
	}

	public long getTotalPage(int psize) {
		long total = authorJpa.count();
		long totalPage = total / psize;
		if (total % psize != 0) {
			totalPage += 1;
		}
		return totalPage;
	}
}
