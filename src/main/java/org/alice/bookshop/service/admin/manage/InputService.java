package org.alice.bookshop.service.admin.manage;

import java.util.List;

import org.alice.bookshop.model.Book_Input;
import org.alice.bookshop.model.Input;
import org.alice.bookshop.repository.InputJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("amInputService")
public class InputService {
	@Autowired
	public InputJpa inputJpa;

	@Autowired
	private Book_InputService biService;

	public List<Input> getInputs(int p, int psize) {
		Pageable pageable = PageRequest.of(p - 1, psize);
		Page<Input> inputs = inputJpa.findAll(pageable);
		return inputs.getContent();
	}

	public String add(Input input) {
		inputJpa.save(input);

		for (Book_Input bi : input.getBook_inputs()) {
			bi.setInput(input);
			biService.book_inputJpa.save(bi);
		}
		return "Add input " + input.getId() + " successed";
	}

	public String edit(Input input) {
		inputJpa.save(input);

		for (Book_Input bi : input.getBook_inputs()) {
			bi.setInput(input);
			biService.book_inputJpa.save(bi);
		}
		return "Edit input id = " + input.getId() + " successed";
	}
}
