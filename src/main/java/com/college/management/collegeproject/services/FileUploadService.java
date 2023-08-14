package com.college.management.collegeproject.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.college.management.collegeproject.model.Subject;
import com.college.management.collegeproject.model.UploadFile;
import com.college.management.collegeproject.repositories.FileUploadRepository;

@Service
public class FileUploadService {
	@Autowired
	private FileUploadRepository fileUploadRepository;
	public UploadFile uploadFile(MultipartFile file,Subject subject) {
		UploadFile uploadFile= new UploadFile();
		UploadFile result=null;
		try {
			uploadFile.setContent(file.getBytes());
			uploadFile.setFileName(file.getOriginalFilename());
			uploadFile.setFileType(file.getContentType());
			uploadFile.setSubject(subject);
			result=fileUploadRepository.save(uploadFile);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	public void deleteById(String id) {
		System.out.println("Delete method");
		fileUploadRepository.deleteByFileId(id);
	}
	public UploadFile getById(String id) {
		return fileUploadRepository.findByFileId(id);
	}

}
