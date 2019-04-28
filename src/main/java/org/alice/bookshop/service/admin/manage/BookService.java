package org.alice.bookshop.service.admin.manage;

import org.alice.bookshop.model.Book;
import org.alice.bookshop.repository.BookJpa;
import org.alice.bookshop.service.utility.StorageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("amBookService")
public class BookService {

	@Autowired
	public BookJpa bookJpa;

	@Autowired
	private StorageFileService sfSvc;

	public Page<Book> getBooks(int p, int psize) {
		return bookJpa.findByDeleted(false, PageRequest.of(p - 1, psize));
	}

	private boolean isBookExit(Book book) {
		Book isExit = bookJpa.findByName(book.getName());
		return (isExit != null);
	}

	public String edit(Book book, MultipartFile file, MultipartFile[] files, MultipartFile[] thumbs,
			Integer[] relateBookIds) {
		Book origin = bookJpa.getOne(book.getId());
		if (!origin.getName().equals(book.getName()) && isBookExit(book)) {
			return "Book's name should not be same with another!";
		} else {
			if (file.getSize() != 0) {
				book.setImgURL("/images/book/" + sfSvc.storageFile(file, "book", book.getName()));
			}

			if (files.length >= 1 && files[0].getSize() > 0) {
				book.getImgURLs().clear();
				for (int i = 0; i <= files.length - 1; i++) {
					MultipartFile iFile = files[i];
					if (iFile.getSize() != 0) {
						book.getImgURLs()
								.add("/images/book/" + sfSvc.storageFile(iFile, "book", book.getName() + "-" + i));
					}
				}
			}

			if (relateBookIds != null) {
				book.getRelateBooks().clear();
				for (int i = 0; i <= relateBookIds.length - 1; i++) {
					int id = relateBookIds[i];
					Book relateBook = bookJpa.getOne(id);
					book.getRelateBooks().add(relateBook);
				}
			}

			int thumbIndex = origin.getThumbURLs().size();
			for (int i = 0; i <= files.length - 1; i++) {
				MultipartFile iFile = files[i];
				if (iFile.getSize() != 0) {
					book.getThumbURLs().add("/images/thumb/"
							+ sfSvc.storageFile(iFile, "book", book.getName() + "-" + (i + thumbIndex)));
				}
			}
			bookJpa.save(book);
			return "Edit book id = " + book.getId() + " successed";
		}
	}

	public String add(Book book, MultipartFile file, MultipartFile[] files, MultipartFile[] thumbs,
			Integer[] relateBookIds) {
		if (isBookExit(book)) {
			return "Book " + book.getName() + " already exit";
		} else {
			if (file.getSize() != 0) {
				book.setImgURL("/images/book/" + sfSvc.storageFile(file, "book", book.getName()));
			}

			for (int i = 0; i <= files.length - 1; i++) {
				MultipartFile iFile = files[i];
				if (iFile.getSize() != 0) {
					book.getImgURLs().add("/images/book/" + sfSvc.storageFile(iFile, "book", book.getName() + "-" + i));
				}
			}

			for (int i = 0; i <= files.length - 1; i++) {
				MultipartFile iFile = files[i];
				if (iFile.getSize() != 0) {
					book.getThumbURLs()
							.add("/images/thumb/" + sfSvc.storageFile(iFile, "book", book.getName() + "-" + i));
				}
			}

			if (relateBookIds != null) {
				for (int i = 0; i <= relateBookIds.length - 1; i++) {
					int id = relateBookIds[i];
					Book relateBook = bookJpa.getOne(id);
					book.getRelateBooks().add(relateBook);
				}
			}

			bookJpa.save(book);
			return "Add book" + book.getName() + " successed";
		}
	}

}
