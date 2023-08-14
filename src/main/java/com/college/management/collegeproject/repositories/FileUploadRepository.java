package com.college.management.collegeproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.college.management.collegeproject.model.UploadFile;

import jakarta.transaction.Transactional;

@Repository
public interface FileUploadRepository extends JpaRepository<UploadFile, String>{
	public UploadFile findByFileId(String fileId);
	@Transactional
	@Modifying
	@Query("DELETE FROM UploadFile uf WHERE uf.fileId = ?1")
	public void deleteByFileId(String fileId);
}
