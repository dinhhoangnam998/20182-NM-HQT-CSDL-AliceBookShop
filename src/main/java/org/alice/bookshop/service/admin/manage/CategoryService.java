package org.alice.bookshop.service.admin.manage;

import org.alice.bookshop.model.Category;
import org.alice.bookshop.repository.CategoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service("amCategoryService")
public class CategoryService {

	@Autowired
	public CategoryJpa categoryJpa;

	public Page<Category> getCategories(int p, int psize) {
		return categoryJpa.findByDeleted(false, PageRequest.of(p - 1, psize));
	}

	private boolean isCategoryExit(Category category) {
		Category isExit = categoryJpa.findByName(category.getName());
		return (isExit != null);
	}

	public String add(Category category) {
		if (isCategoryExit(category)) {
			return "Category " + category.getName() + " already exit!";
		} else {
			categoryJpa.save(category);
			return "Add category " + category.getName() + " successed";
		}
	}

	public String edit(Category category) {
		Category originCategory = categoryJpa.getOne(category.getId());
		if (isCategoryExit(category) && !originCategory.getName().equals(category.getName())) {
			return "Category's name should not be same with another!";
		} else {
			categoryJpa.save(category);
			return "Edit category id = " + category.getId() + " successed";
		}
	}

}
