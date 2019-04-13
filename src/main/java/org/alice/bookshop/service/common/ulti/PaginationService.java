package org.alice.bookshop.service.common.ulti;

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
	private int curPage = DEFAULT_PAGE;
	private int pageSize = DEFAULT_PAGE_SIZE;
	private int totalPage, lastPage;

	public void process(HttpSession ss, int reqPage, int psize, long totalRecord) {
		if (reqPage < 1) {
			curPage = DEFAULT_PAGE;
		} else {
			curPage = reqPage;
		}

		if (psize < 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		} else if (psize != pageSize) {
			pageSize = psize;
		}

		lastPage = totalPage = (int) Math.ceil(totalRecord / (double) pageSize);

		ss.setAttribute("curPage", curPage);
		ss.setAttribute("pageSize", pageSize);
		ss.setAttribute("lastPage", lastPage);
	}

	public List<Integer> getPageList() {
		List<Integer> pageList = new ArrayList<>();
		int numberOfBucket = (int) Math.ceil(totalPage / 10.0);
		int reqBucket = curPage / 10;

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
