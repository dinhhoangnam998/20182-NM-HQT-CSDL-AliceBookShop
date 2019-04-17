package org.alice.bookshop.service.utility;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageFileService {

	public String storageFile(MultipartFile mtpf, String dir, String name) {
		String fileName = name + mtpf.getOriginalFilename().substring(mtpf.getOriginalFilename().lastIndexOf("."));

		File dest = new File("H:/upload/" + dir + "/" + fileName);

		try {
			mtpf.transferTo(dest);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileName;
	}

}
