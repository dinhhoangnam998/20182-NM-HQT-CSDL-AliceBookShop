package org.alice.bookshop.service.utility;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
@Setter
@Getter
public class PaginationService {

	final static int DEFAULT_PAGE = 1;
	final static int DEFAULT_PAGE_SIZE = 15;
	private int requestPage = DEFAULT_PAGE;
	private int pageSize = DEFAULT_PAGE_SIZE;
	private long lastPage;
	private HttpSession ss;

	public void validate(HttpSession ss, int reqPage, int psize) {
		if (reqPage < 1) {
			requestPage = DEFAULT_PAGE;
		} else {
			requestPage = reqPage;
		}

		if (psize < 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		} else if (psize != pageSize) {
			pageSize = psize;
		}
		this.ss = ss;
		ss.setAttribute("curPage", requestPage);
		ss.setAttribute("pageSize", pageSize);
	}

	public List<Integer> getPageList(long totalPage) {
		this.lastPage = totalPage;
		this.ss.setAttribute("lastPage", totalPage);
		List<Integer> pageList = new ArrayList<>();
		int numberOfBucket = (int) Math.ceil(totalPage / 10.0);
		int reqBucket = requestPage / 10;

		if (reqBucket == (numberOfBucket - 1)) {
			for (int i = reqBucket * 10 - 1; i <= totalPage + 1; i++) {
				if (i != 0) {
					pageList.add(i);
				}
			}
		} else {
			for (int i = reqBucket * 10 - 1; i <= reqBucket * 10 + 10; i++) {
				if (i != 0) {
					pageList.add(i);
				}
			}
		}

		return pageList;
	}
}
