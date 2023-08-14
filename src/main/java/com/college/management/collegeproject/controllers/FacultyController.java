package com.college.management.collegeproject.controllers;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.college.management.collegeproject.model.Assignment;
import com.college.management.collegeproject.model.AssignmentSubmission;
import com.college.management.collegeproject.model.AttendanceRecords;
import com.college.management.collegeproject.model.Faculty;
import com.college.management.collegeproject.model.Student;
import com.college.management.collegeproject.model.Subject;
import com.college.management.collegeproject.model.UploadFile;
import com.college.management.collegeproject.model.Users;
import com.college.management.collegeproject.repositories.StudentRepository;
import com.college.management.collegeproject.repositories.UsersRepository;
import com.college.management.collegeproject.services.AssignmentService;
import com.college.management.collegeproject.services.AssignmentSubmissionService;
import com.college.management.collegeproject.services.AttendanceRecordsService;
import com.college.management.collegeproject.services.FacultyService;
import com.college.management.collegeproject.services.FileUploadService;
import com.college.management.collegeproject.services.StudentService;
import com.college.management.collegeproject.services.SubjectService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/faculty")
public class FacultyController {
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private AttendanceRecordsService attendanceRecordsService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private AssignmentService assignmentService;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private AssignmentSubmissionService assignmentSubmissionService;

	@RequestMapping("/loadDetails")
	public String loadDetails(Authentication authentication, @RequestParam(required = false) String yearSem,
			Model model, HttpSession session) {
		if (yearSem != null)
			session.setAttribute("yearSem", yearSem);
		String name = authentication.getName();
		Users user = usersRepository.findByuserName(name);
		Faculty faculty = facultyService.getByEmail(user.getEmail());
		List<Subject> subjects = faculty.getSubjects().stream()
				.filter(sub -> sub.getYearSem().equals(session.getAttribute("yearSem").toString())).toList();
		model.addAttribute("subjects", subjects);
		return "facultySubjectLoad";

	}

	@RequestMapping("/courseContent")
	public String getCourseContent(@RequestParam String code, Model model, HttpSession session) {
		Subject subject = subjectService.getSubjectById(code);
		model.addAttribute("subName", subject);
		session.setAttribute("subject", subject);
		model.addAttribute("assignment", new Assignment());
		return "subjectDetails";

	}

	@PostMapping("/getStudents")
	public String getStudentList(@RequestParam String batch, @RequestParam String section, HttpSession session) {
		session.setMaxInactiveInterval(0);
		Subject sub = (Subject) session.getAttribute("subject");
		List<Student> students = sub.getStudents();
		List<Student> filteredStudents = students.stream()
				.filter(s -> s.getBatch().equals(batch) && s.getSection().equals(section)).toList();
		session.setAttribute("filteredStudents", filteredStudents);
		@SuppressWarnings("unchecked")
		List<Student> retrievedFilteredStudents = (List<Student>) session.getAttribute("filteredStudents");
		System.out.println("Students Enrolled #retrived from session: " + retrievedFilteredStudents.toString());
		return "redirect:/faculty/courseContent?code=" + sub.getSubjectCode();
	}

	@GetMapping("/attendanceform")
	public String getAttendanceForm(HttpSession session, Model model, @RequestParam(required = false) String error) {
		String errorMessage = "";
		if (error != null)
			errorMessage = "Attendance Submitted Successfully";
		@SuppressWarnings("unchecked")
		List<Student> enrolledStudents = (List<Student>) session.getAttribute("filteredStudents");
		Subject sub = (Subject) session.getAttribute("subject");
		model.addAttribute("subCode", sub.getSubjectCode());
		model.addAttribute("enrolledStudents", enrolledStudents);
		model.addAttribute("errorMessage", errorMessage);
		return "attendanceForm";
	}

	@PostMapping("/submitAttendance")
	public String submitAttendance(HttpServletRequest req, HttpSession session) {
		Subject subject = (Subject) session.getAttribute("subject");
		Subject mySubject = subjectService.getSubjectById(subject.getSubjectCode());
		Enumeration<String> params = req.getParameterNames();
		AttendanceRecords attendanceRecords;
		@SuppressWarnings("unchecked")
		List<Student> students = (List<Student>) session.getAttribute("filteredStudents");
		List<Integer> Ids = students.stream().map(s -> s.getsId()).toList();
		List<Student> studentList = studentService.getById(Ids);
		int i = 0;
		while (params.hasMoreElements() && i < studentList.size()) {
			attendanceRecords = new AttendanceRecords();
			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			attendanceRecords.setDate(currentDate.format(formatter));
			attendanceRecords.setStudent(studentList.get(i));
			String ele = params.nextElement();
			String element = req.getParameter(ele).toString();
			System.out.println(element);
			if (element.equals("absent")) {
				attendanceRecords.setIsPresent(0);
			} else {
				attendanceRecords.setIsPresent(1);
			}
			attendanceRecords.setSubject(mySubject);
			attendanceRecordsService.saveAttendanceRecord(attendanceRecords);
			i++;
		}
		return "redirect:/faculty/attendanceform?error=true";
	}

	@PostMapping("/uploadAssignment")
	public String uploadAssignment(@ModelAttribute(name = "assignment") Assignment assignment, HttpSession session) {
		Subject subject = (Subject) session.getAttribute("subject");
		@SuppressWarnings("unchecked")
		List<Student> students = (List<Student>) session.getAttribute("filteredStudents");
		Subject mySubject = subjectService.getSubjectById(subject.getSubjectCode());
		assignment.setSubject(mySubject);
		assignment.setDept(subject.getSubDepartment().getName());
		assignment.setYearSem(subject.getYearSem());
		assignment.setBatch(students.get(0).getBatch());
		assignment.setSection(students.get(0).getSection());
		Assignment status = assignmentService.uploadAssignment(assignment);
		if (status != null && status.getAssignmentId() > 0) {
			System.out.println("Assignment upload is success");
			return "redirect:/faculty/courseContent?code=" + subject.getSubjectCode();
		}
		System.out.println("Assignment not uploaded");
		return "";
	}

	@RequestMapping("/documents")
	public String uploadDocuments(HttpSession session, Model model,
			@RequestParam(name = "error", required = false) String error) {
		String errorMessage = "";
		if (error != null)
			errorMessage = "File Uploaded Successfully!!";
		Subject subject = (Subject) session.getAttribute("subject");
		Subject mySubject = subjectService.getSubjectById(subject.getSubjectCode());
		model.addAttribute("subCode", subject.getSubjectCode());
		model.addAttribute("documents", mySubject.getDocumentUploads());
		model.addAttribute("errorMessage", errorMessage);
		System.out.println(subject.getDocumentUploads().toString());
		return "documentUpload";
	}

	@PostMapping("/uploadDocuments")
	public String uploadFile(@RequestParam(name = "file") MultipartFile file, HttpSession session) {
		Subject subject = (Subject) session.getAttribute("subject");
		Subject mySubject = subjectService.getSubjectById(subject.getSubjectCode());
		UploadFile result = fileUploadService.uploadFile(file, mySubject);
		if (result != null)
			return "redirect:/faculty/documents?error=true";
		return "redirect:/faculty/documents";
	}

	@RequestMapping("/removeFile/{id}")
	public String removeUploadedFile(@PathVariable(name = "id") String id) {
		fileUploadService.deleteById(id);
		return "redirect:/faculty/documents";
	}

	@GetMapping("/downloadFile")
	@PreAuthorize("hasRole('FACULTY')")
	public void downloadFile(@RequestParam(name = "id") String id, HttpServletResponse response) throws IOException {
		/*
		 * UriComponents uri=UriComponentsBuilder.newInstance() .scheme("http")
		 * .host("localhost") .port(8080) .path("/api/download/{id}")
		 * .buildAndExpand(id); ResponseEntity<byte[]>
		 * response=restTemplate.getForEntity(uri.toUriString(), byte[].class);
		 */
		System.out.println("Downloading..");
		UploadFile file = fileUploadService.getById(id);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=" + file.getFileName());
		response.getOutputStream().write(file.getContent());

	}

	@GetMapping("/fetchallassignments")
	public String fetchSubmissions(Model model, HttpSession session) {

		Subject subject = (Subject) session.getAttribute("subject");

		model.addAttribute("assignments", new HashSet<Assignment>(subject.getAssigenments()));
		model.addAttribute("subCode", subject.getSubjectCode());
		System.out.println("Assignments : " + subject.getAssigenments().toString());
		return "facultyAssignmentResponse";
	}

	@GetMapping("/getsubmissions")
	public String getSubmissions(Model model, HttpSession session, @RequestParam int id) {
		@SuppressWarnings("unchecked")
		List<Student> students = (List<Student>) session.getAttribute("filteredStudents");
		List<AssignmentSubmission> submissions = new ArrayList<>();
		for (Student s : students) {
			submissions.addAll(s.getAssignmentSubmissions().stream()
					.filter(submission -> submission.getAssignment().getAssignmentId() == id).toList());
		}
		model.addAttribute("submissions", submissions);
		System.out.print("Submissions : " + submissions.toString());
		return "submissionview";
	}

	@GetMapping("/downloadresponse")
	public void downloadReponse(@RequestParam(name = "id") int id, HttpServletResponse response) {
		Optional<AssignmentSubmission> submission = assignmentSubmissionService.getResponseById(id);
		if (submission.isPresent()) {
			AssignmentSubmission myResponse = submission.get();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=" + myResponse.getDate());
			try {
				response.getOutputStream().write(myResponse.getContent());
			} catch (IOException e) {
				System.out.println("Error Occured : " + e.getMessage());
			}
		}
	}

}
