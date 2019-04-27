package org.alice.bookshop.service.user.shopping;

import java.util.ArrayList;
import java.util.List;

import org.alice.bookshop.model.Author;
import org.alice.bookshop.repository.AuthorJpa;
import org.alice.bookshop.repository.CategoryJpa;
import org.alice.bookshop.repository.PublisherJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service("usCategoryService")
public class NavigateService {

	public final static int NUM_OF_ROW = 3;
	public final static int NUM_OF_COl = 4;

	@Autowired
	public AuthorJpa authorJpa;

	@Autowired
	public PublisherJpa publisherJpa;

	@Autowired
	public CategoryJpa categoryJpa;

	public Page<Author> getAuthors(int p, int psize) {
		return authorJpa.findByDeleted(false, PageRequest.of(p - 1, psize));
	}

	public List<List<Author>> getAuthorRows(List<Author> authors) {
		List<List<Author>> authorRows = new ArrayList<List<Author>>();
		
		for(int i = 0; i <= NUM_OF_ROW - 1; i++) {
			List<Author> authorRow = new ArrayList<>();
			for(int j = i * NUM_OF_COl; j <= i * NUM_OF_COl + 3; j++) {
				authorRow.add(authors.get(j));
			}
			authorRows.add(authorRow);
		}
		return authorRows;
	}

}
