package com.sandhya.studentmanagement.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sandhya.studentmanagement.entity.Student;
import com.sandhya.studentmanagement.entity.User;
import com.sandhya.studentmanagement.repository.StudentRepository;
import com.sandhya.studentmanagement.repository.UserRepository;
import com.sandhya.studentmanagement.service.StudentService;
import com.sandhya.studentmanagement.service.UserService;

@Controller
@RequestMapping("/admin/students")
public class StudentController {
	@Autowired
	private StudentService studentService;

	@Autowired
	private StudentRepository studentRepository;
	@GetMapping("/")
	public String listUser(Model model ) {
		List<Student> students = studentRepository.findAll();
		model.addAttribute("students",students );
		return "/admin/students/index";

	}

	@GetMapping("/add")
	public String addStudents(Model model) {
		model.addAttribute("student",new Student());
		return "/admin/students/add";

	}
	@PostMapping("/add")
	public String addStudents(@ModelAttribute Student student) {
	
		studentService.saveStudent(student);
		return "redirect:/admin/students/"; // Redirects to the user list after creation
	}
	@GetMapping("/edit/{id}")
	public String editStudentForm(@PathVariable int id, Model model) {
		Optional<Student> optionalStudent = studentRepository.findById(id);
		if (optionalStudent.isPresent()) {
			
			model.addAttribute("student", optionalStudent.get());
			return "admin/students/edit"; // Returns the edit user form
		}
		return "redirect:/admin/students"; // Redirects to user list if ID not found
	}

	// Handle the update of an existing user
	@PostMapping("/update/{id}")
    public String updateStudent(@PathVariable int id, @ModelAttribute Student updatedStudent, Model model) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();
            student.setName(updatedStudent.getName());
            student.setAddress(updatedStudent.getAddress());
            student.setParentContactInfo(updatedStudent.getParentContactInfo());
            student.setGrade(updatedStudent.getGrade());
          
            studentRepository.save(student); 
        }
        return "redirect:/admin/students";  
    }
	@GetMapping("/delete/{id}")
	public String deletUser(@PathVariable int id, Model model) {
		Optional<Student> student = studentRepository.findById(id);
		if(student.isPresent()) {
			studentRepository.delete(student.get());
			model.addAttribute("message", "User Deleted Sucessfully");
		}
		return "redirect:/admin/students"; // Redirects to user list if ID not found
	}
	
}
