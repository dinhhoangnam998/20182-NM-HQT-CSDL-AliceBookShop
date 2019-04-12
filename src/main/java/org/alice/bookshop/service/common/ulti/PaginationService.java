package org.alice.bookshop.service.common.ulti;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PaginationService {

	public List<Integer> getPageList(long totalPage, int p, int psize) {
		List<Integer> pageList = new ArrayList<>();
		long lastBucket = totalPage / 10;
		if (totalPage % 10 != 0) {
			lastBucket += 1;
		}

		int reqBucket = p / 10;

		if (reqBucket == (lastBucket - 1)) {
			for (int i = reqBucket * 10 - 1; i <= totalPage + 1; i++) {
				if(i != 0) {
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
