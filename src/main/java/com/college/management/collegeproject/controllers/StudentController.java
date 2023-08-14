package com.college.management.collegeproject.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.college.management.collegeproject.model.Assignment;
import com.college.management.collegeproject.model.AttendanceRecords;
import com.college.management.collegeproject.model.Student;
import com.college.management.collegeproject.model.Subject;
import com.college.management.collegeproject.model.UploadFile;
import com.college.management.collegeproject.model.Users;
import com.college.management.collegeproject.repositories.UsersRepository;
import com.college.management.collegeproject.services.AssignmentService;
import com.college.management.collegeproject.services.AssignmentSubmissionService;
import com.college.management.collegeproject.services.FileUploadService;
import com.college.management.collegeproject.services.StudentService;
import com.college.management.collegeproject.services.SubjectService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private StudentService studentService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private AssignmentService assignmentService;
	@Autowired
	private AssignmentSubmissionService assignmentSubmissionService;
	@RequestMapping("/studentSubjects")
	public String loadDetails(@RequestParam(name="yearSem",required = false) String yearSem,Model model,Authentication authentication,HttpSession session) {
		if(yearSem!=null)
			session.setAttribute("yearSem", yearSem);
		Users user=usersRepository.findByuserName(authentication.getName());
		Student student=studentService.getStudentByMailId(user.getEmail());
		List<Subject> studentSubjects=student.getSubjects();
		List<Subject> filteredStudentSubjects=studentSubjects.stream().filter(sub->sub.getYearSem().equals(session.getAttribute("yearSem").toString())).toList();
		model.addAttribute("studentSubjects", filteredStudentSubjects);
		session.setAttribute("student", student);
		return "studentSubjectLoad";
	}
	@GetMapping("/getCourse")
	public String getCourseContent(@RequestParam(name = "code") String code,Model model,HttpSession session) {
		Subject subject=subjectService.getSubjectById(code);
		model.addAttribute("subName",subject);
		session.setAttribute("subject", subject);
		return "studentSubjectDetail";
	}
	@GetMapping("/showAttendance")
	public String showAttendance(Model model,HttpSession session) {
		Student student=(Student)session.getAttribute("student");
		Subject subject=(Subject)session.getAttribute("subject");
		List<AttendanceRecords> attendanceRecords=student.getAttendanceRecords().stream().filter(attendance->attendance.getSubject().getSubjectCode().equals(subject.getSubjectCode())).toList();
		model.addAttribute("attendanceRecords", attendanceRecords);
		model.addAttribute("subject",subject);
		return "studentAttendance";
	}
	@GetMapping("/showDocuments")
	public String showDownloads(Model model,HttpSession session) {
		Subject subject=(Subject)session.getAttribute("subject");
		model.addAttribute("documents", subject.getDocumentUploads());
		model.addAttribute("subCode",subject.getSubjectCode());
		return "studentDownloads";
	}
	@GetMapping("/getfile")
	public void downloadFile(@RequestParam(name="id") String id,HttpServletResponse response) throws IOException {
		UploadFile file=fileUploadService.getById(id);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename="+file.getFileName());
		response.getOutputStream().write(file.getContent());
	}
	@GetMapping("/fetchassignments")
	public String getAssignments(Model model,HttpSession session) {
		Subject subject=(Subject)session.getAttribute("subject");
		model.addAttribute("subCode",subject.getSubjectCode());
		model.addAttribute("assignments", new HashSet<Assignment>(subject.getAssigenments()));
		return "studentAssignmentsDisplay";
	}
	@GetMapping("/openassignment")
	public String openAssignment(Model model,HttpSession session,@RequestParam(name="num")int num,
			@RequestParam(name="saved",required=false) String saved,@RequestParam(name="notSaved",required=false) String notSaved) {
		String errorMessage="";
		if(saved!=null)
			errorMessage="Assignment Submitted Successfully!!";
		else if(notSaved!=null)
			errorMessage="OPPS It Seems This Assignment is Already Submitted!!";
		Subject subject=(Subject)session.getAttribute("subject");
		Student student=(Student)session.getAttribute("student");
		String section=student.getSection();
		String batch=student.getBatch();
		List<Assignment> assignments=subject.getAssigenments();
		List<Assignment> resultAssignment=assignments.stream().filter(a->a.getAssignmentNumber()==num&&a.getBatch().equals(batch)&&a.getSection().equals(section)).toList();
		String questions=resultAssignment.get(0).getQuestions();
		System.out.println("Questions : "+questions);
		model.addAttribute("subCode",subject.getSubjectCode());
		model.addAttribute("questions", questions);
		model.addAttribute("errorMessage",errorMessage);
		session.setAttribute("assignmentId", resultAssignment.get(0).getAssignmentId());
		return "assignmentSubmission";
	}
	@PostMapping("/submitassignment")
	public String submitAssignment(Model model,HttpSession session,@RequestParam(name="assignmentFile")MultipartFile assignmentFile) {
		Student student=(Student)session.getAttribute("student");
		Student myStudent=studentService.getStudentByRollNum(student.getRollNum());
		int id=Integer.parseInt(session.getAttribute("assignmentId").toString());
		Optional<Assignment> optional=assignmentService.getById(id);
		if(optional.isPresent()) {
			Assignment assignment=optional.get();
			try {
				
				assignmentSubmissionService.submitAssignment(myStudent, assignment,assignmentFile);
			} catch (Exception e) {
				System.out.println("Error : "+e.getMessage());
				return "redirect:/student/openassignment?num="+optional.get().getAssignmentNumber()+"&notSaved=true";
			}
		}
		return "redirect:/student/openassignment?num="+optional.get().getAssignmentNumber()+"&saved=true";
	}

}
