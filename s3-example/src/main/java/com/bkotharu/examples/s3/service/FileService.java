package com.bkotharu.examples.s3.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bkotharu.examples.s3.utils.FileUtils;

@Service
public class FileService {

	@Autowired
	private S3Client s3Client;

	public String uploadFile(MultipartFile multipartFile) {
		String fileUrl = "";
		try {
			File file = FileUtils.convertMultiPartToFile(multipartFile);
			fileUrl = s3Client.uploadFileTos3bucket(multipartFile.getOriginalFilename(), file);
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileUrl;
	}

	public List<String> getFileNames() {
		return s3Client.getFilesListFromS3Bucket();
	}

	public File getFile(String fileName) throws Exception {
		return s3Client.getFileFromS3Bucket(fileName);
	}

	public void deleteFile(String fileName) {
		s3Client.deleteFileFromS3Bucket(fileName);
	}
}
