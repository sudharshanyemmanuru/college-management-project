package com.college.management.collegeproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.college.management.collegeproject.model.Department;
import com.college.management.collegeproject.model.Faculty;
import com.college.management.collegeproject.model.Role;
import com.college.management.collegeproject.model.Roles;
import com.college.management.collegeproject.model.Student;
import com.college.management.collegeproject.model.Subject;
import com.college.management.collegeproject.model.Users;
import com.college.management.collegeproject.repositories.RolesRepository;
import com.college.management.collegeproject.repositories.UsersRepository;
import com.college.management.collegeproject.services.DepartmentService;
import com.college.management.collegeproject.services.FacultyService;
import com.college.management.collegeproject.services.StudentService;
import com.college.management.collegeproject.services.SubjectService;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
@RequestMapping(value="/admin")
public class AdminController {
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private FacultyService facultyService;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private RolesRepository rolesRepository;
	@Autowired
	private StudentService studentService;
	@Autowired
	private SubjectService subjectService;
	@Value("${collge.name}")
	private String collegeName;
	@PostMapping("/addDepartment")
	public String addDepartment(@RequestParam String dept, Model model, Authentication auth) {
		String isSuccess="";
		Department d = new Department();
		d.setName(dept);
		try {
			Department d1 = departmentService.saveDepartment(d);
			if (d1 != null && d1.getDepartment_id() > 0) {
				isSuccess="Success";
				model.addAttribute("isSuccess", isSuccess);
				System.out.println("College Name is :"+collegeName);
				return "redirect:/home";
			}
		} catch (Exception ex) {
			System.out.println("Department Not Added " + ex.getMessage());
		}
		
		return "redirect:/home";
	}
	@GetMapping("/facultyManagement")
	public String getFacultyManage(Model model) {
		model.addAttribute("faculty",new Faculty());
		List<Faculty> faculties=facultyService.getAll();
		model.addAttribute("faculties",faculties);
		return "facultyManage";
	}
	@GetMapping("/back")
	public String goToDashBoard() {
		return "redirect:/home";
	}
	@PostMapping("/saveFaculty")
	public String saveFacultyDetails(@ModelAttribute Faculty faculty,@RequestParam String dept,@RequestParam String pwd){
		Department dep=departmentService.getDepartmentByName(dept);
		faculty.setDepartment(dep);
		Faculty f=facultyService.saveDetails(faculty);
		Users user= new Users(faculty.getEmail(),faculty.getName(),pwd);
		Roles role=rolesRepository.getById(Role.FACULTY.getRole());
		user.setRoles(role);
		usersRepository.save(user);
		if(f!=null && f.getFacultyId()>0) {
			return "redirect:/admin/facultyManagement";
		}
		return "redirect:/admin/facultyManagement";
		
	}
	@GetMapping("/studentManagement")
	public String getStudentManage(Model model) {
		model.addAttribute("student",new Student());
		List<Student> students=studentService.getAll();
		model.addAttribute("students",students);
		return "studentManagement";
	}
	@PostMapping("/saveStudent")
	public String saveStudentDetails(@ModelAttribute Student student,@RequestParam String sdept,@RequestParam String spwd) {
		Department d=departmentService.getDepartmentByName(sdept);
		if(d!=null) {
			student.setDepartment(d);
			Student saved=studentService.saveStudentDetails(student);
			String emailId=student.getRollNum()+"@college.com";
			Users user= new Users(emailId,student.getName(),spwd);
			Roles role=rolesRepository.getById(Role.STUDENT.getRole());
			user.setRoles(role);
			Users savedUser=usersRepository.save(user);
			if((saved!=null&&saved.getsId()>1) &&(savedUser!=null && savedUser.getUser_id()>1))
				return "redirect:/admin/studentManagement";
		}
		return "redirect:/admin/studentManagement";
	}
	@GetMapping("/subjectManagement")
	public String getSubjectManage(Model model) {
		model.addAttribute("subject",new Subject());
		List<Department> departments=departmentService.getAll();
		model.addAttribute("departments",departments);
		return "subjectManagement";
	}
	@PostMapping("/saveSubject")
	public String saveSubject(@ModelAttribute Subject subject,@RequestParam String subDept) {
		Department dept=departmentService.getDepartmentByName(subDept);
		if(dept!=null) {
			subject.setSubDepartment(dept);
			Subject savedSubject=subjectService.saveSubjectDetails(subject);
			if(savedSubject!=null && savedSubject.getSubjectId()>1) {
				List<Subject> subjects=dept.getSubjects();
				System.out.println("Subjects "+subjects);
				return "redirect:/admin/subjectManagement";
			}
		}
		
		return "redirect:/admin/subjectManagement";
		
	}
	@GetMapping("/getSubjects")
	public String getSubjects(Model model,@RequestParam String deptNam,HttpSession session) {
		Department dept=departmentService.getDepartmentByName(deptNam);
		System.out.println(dept.getName());
		List<Subject> subjects=dept.getSubjects();
		System.out.println(subjects);
		model.addAttribute("subjects",subjects);
		model.addAttribute("deptName",dept.getName());
		session.setAttribute("department", dept);
		return "subjectLoad";
		
	}
	@GetMapping("/gotoSubjectManagement")
	public String getSubjectManagementpage() {
		return "redirect:/admin/subjectManagement";
	}
	@GetMapping("/viewFaculties")
	public String getFaculties(@RequestParam String subCode,Model model,HttpSession session) {
		model.addAttribute("faculty",new Faculty());
		Subject subject=subjectService.getSubjectById(subCode);
		List<Faculty> faculties=subject.getFaculties();
		model.addAttribute("faculties",faculties);
		session.setAttribute("subject", subject);
		return "facultySubject";
	}
	@GetMapping("/goToSubjects")
	public String goTOSubjects(HttpSession session) {
		Department dept=(Department) session.getAttribute("department");
		return "redirect:/admin/getSubjects?deptNam="+dept.getName();
	}
	@PostMapping("/enrollFaculty")
	public String enrollFaculty(@RequestParam String facultyEmailId,HttpSession session) {
		Faculty faculty=facultyService.getByEmail(facultyEmailId);
		Subject subject=(Subject) session.getAttribute("subject");
		faculty.getSubjects().add(subject);
		subject.getFaculties().add(faculty);
		Faculty f=facultyService.saveDetails(faculty);
		if(f!=null) {
			return "redirect:/admin/viewFaculties?subCode="+subject.getSubjectCode();
		}
		return "";
	}
	@PostMapping("/unEnrollFaculty")
	public String unEnrollFaculty(@RequestParam String facultyEmailId,HttpSession session) {
		Faculty faculty=facultyService.getByEmail(facultyEmailId);
		Subject subject=(Subject)session.getAttribute("subject");
		boolean r1=faculty.getSubjects().remove(subject);
		boolean r2=subject.getFaculties().remove(faculty);
		if(r1&r2) {
			Faculty saved=facultyService.saveDetails(faculty);
			if(saved!=null) {
				return "redirect:/admin/viewFaculties?subCode="+subject.getSubjectCode();
			}
		}
		return "";
	}
	@GetMapping("/viewStudents")
	public String getStudents(@RequestParam String subCode,Model model,HttpSession session) {
		Subject subject = subjectService.getSubjectById(subCode);
		List<Student> students=subject.getStudents();
		model.addAttribute("students",students);
		session.setAttribute("studentSubject", subject);
		return "studentSubject";
	}
//	@PostMapping("/enrollAll")
//	public String enrollAllStudents(HttpSession session) {
//		Subject subject=(Subject) session.getAttribute("studentSubject");
//		List<Student> students=(List<Student>) session.getAttribute("studentDetails");
//		if(students!=null) {
//			for(Student s:students) {
//				s.getSubjects().add(subject);
//				subject.getStudents().add(s);
//				studentService.saveStudentDetails(s);
//			}
//		}else {
//			System.out.println("Students are not able fetch from the session");
//			
//		}
//		return "redirect:/admin/viewStudents?subCode="+subject.getSubjectCode();
//	}
//	@PostMapping("/unEnrollAll")
//	public String unEnrollAll(HttpSession session) {
//		Subject subject=(Subject)session.getAttribute("studentSubject");
//		Department dept=subject.getSubDepartment();
//		return ""; 
//	}
//	@PostMapping("/getStudentsByBatch")
//	public String getStudentsByBatch(@RequestParam String batchSelect,HttpSession session) {
//		System.out.println("Getting students based on "+batchSelect);
//		Subject subject=(Subject) session.getAttribute("studentSubject");
//		Department dept=subject.getSubDepartment();
//		
//		List<Student> students=studentService.getStudentsByDepartmentAndBatch(dept, batchSelect);
//		session.setAttribute("studentsDetails", students);
//		System.out.println("Students are : "+students.toString());
//		return "redirect:/admin/viewStudents?subCode="+subject.getSubjectCode();
//	}
	@PostMapping("/enrollStudent")
	public String enrollStudent(HttpSession session,@RequestParam String studentRollNum) {
		Student student=studentService.getStudentByRollNum(studentRollNum);
		Subject  subject =(Subject)session.getAttribute("studentSubject");
		student.getSubjects().add(subject);
		subject.getStudents().add(student);
		Student saved=studentService.saveStudentDetails(student);
		if(saved!=null) {
			return "redirect:/admin/viewStudents?subCode="+subject.getSubjectCode();
		}
		return "redirect:/admin/viewStudents?subCode="+subject.getSubjectCode();
		
	}
	@PostMapping("/unEnrollStudent")
	public String unEnrollStudent(HttpSession session,@RequestParam String studentRollNum) {
		Student student=studentService.getStudentByRollNum(studentRollNum);
		Subject subject=(Subject)session.getAttribute("studentSubject");
		student.getSubjects().remove(subject);
		subject.getStudents().remove(student);
		Student saved = studentService.saveStudentDetails(student);
		if(saved!=null) {
			return "redirect:/admin/viewStudents?subCode="+subject.getSubjectCode();
		}
		return "redirect:/admin/viewStudents?subCode="+subject.getSubjectCode();
	}
	

}
