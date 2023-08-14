package com.college.management.collegeproject.restAPIs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.college.management.collegeproject.model.UploadFile;
import com.college.management.collegeproject.services.FileUploadService;

@RestController
@RequestMapping("/api")
public class UploadFileController {
	@Autowired
	private FileUploadService fileUploadService;
	@GetMapping("/download/{id}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable(name="id") String id){
		System.out.println("Rest Api is invoked");
		UploadFile file=fileUploadService.getById(id);
		return ResponseEntity.ok()
			.contentType(MediaType.parseMediaType(file.getFileType()))
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+file.getFileName())
			.body(file.getContent());
	}

}
