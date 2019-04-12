package org.alice.bookshop.service.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Category;
import org.alice.bookshop.repository.CategoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("amCategoryService")
public class CategoryService {
	@Autowired
	public CategoryJpa categoryJpa;

	private boolean isCategoryExit(Category category) {
		Category isExit = categoryJpa.findByName(category.getName());
		return (isExit != null);
	}

	public List<Category> getCategories(int p, int psize) {
		Pageable pageable = PageRequest.of(p - 1, psize);
		Page<Category> categorys = categoryJpa.findAll(pageable);
		return categorys.getContent();
	}

	public String add(Category category) {
		if (isCategoryExit(category)) {
			return "Category already exit";
		} else {
			categoryJpa.save(category);
			return "Add category successed";
		}
	}

	public String edit(Category category) {
		if (isCategoryExit(category)) {
			return "Category already exit";
		} else {
			categoryJpa.save(category);
			return "Edit category successed";
		}
	}

	public long getTotalPage(int psize) {
		long total = categoryJpa.count();
		long totalPage = total / psize;
		if (total % psize != 0) {
			totalPage += 1;
		}
		return totalPage;
	}
}
