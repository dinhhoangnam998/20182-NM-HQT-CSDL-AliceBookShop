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

	public List<Author> getAuthors(int p, int psize) {
		Pageable pageable = PageRequest.of(p - 1, psize);
		Page<Author> authors = authorJpa.findByDeleted(false, pageable);
		return authors.getContent();
	}

	private boolean isAuthorExit(Author author) {
		Author isExit = authorJpa.findByName(author.getName());
		return (isExit != null);
	}

	public String add(Author author) {
		if (isAuthorExit(author)) {
			return "Author " + author.getName() + " already exit!";
		} else {
			authorJpa.save(author);
			return "Add author " + author.getName() + " successed";
		}
	}

	public String edit(Author author) {
		Author originAuthor = authorJpa.getOne(author.getId());
		if (isAuthorExit(author) && !originAuthor.getName().equals(author.getName())) {
			return "Author's name should not be same with another!";
		} else {
			authorJpa.save(author);
			return "Edit author id = " + author.getId() + " successed";
		}
	}

}
