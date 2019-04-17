package org.alice.bookshop.service.admin.manage;

import org.alice.bookshop.model.Author;
import org.alice.bookshop.repository.AuthorJpa;
import org.alice.bookshop.service.utility.StorageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("amAuthorService")
public class AuthorService {

	@Autowired
	public AuthorJpa authorJpa;

	@Autowired
	private StorageFileService sfSvc;

	public Page<Author> getAuthors(int p, int psize) {
		return authorJpa.findByDeleted(false, PageRequest.of(p - 1, psize));
	}

	private boolean isAuthorExit(Author author) {
		Author isExit = authorJpa.findByName(author.getName());
		return (isExit != null);
	}

	public String add(Author author, MultipartFile file) {
		if (isAuthorExit(author)) {
			return "Author " + author.getName() + " already exit!";
		} else {
			if (file.getSize() != 0) {
				author.setImgURL("/images/author/" + sfSvc.storageFile(file, "author", author.getName()));
			}
			authorJpa.save(author);
			return "Add author " + author.getName() + " successed";
		}
	}

	public String edit(Author author, MultipartFile file) {
		Author originAuthor = authorJpa.getOne(author.getId());
		if (!originAuthor.getName().equals(author.getName()) && isAuthorExit(author)) {
			return "Author's name should not be same with another!";
		} else {
			if (file.getSize() != 0) {
				author.setImgURL("/images/author/" + sfSvc.storageFile(file, "author", author.getName()));
			}
			authorJpa.save(author);
			return "Edit author id = " + author.getId() + " successed";
		}
	}

}
