package com.bkotharu.examples.s3.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bkotharu.examples.s3.service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {

	@Autowired
	private FileService fileService;

	/**
	 * Upload File
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping
	public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
		return fileService.uploadFile(file);
	}

	/**
	 * Get File List
	 * 
	 * @return
	 */
	@GetMapping("/names")
	public List<String> getFileNames() {
		return fileService.getFileNames();
	}

	/**
	 * Get File by Name
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/{fileName}")
	public File getFileByName(@PathVariable String fileName) throws Exception {
		return fileService.getFile(fileName);
	}

	@DeleteMapping("/{fileName}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteFile(@PathVariable String fileName) {
		fileService.deleteFile(fileName);
	}
}
