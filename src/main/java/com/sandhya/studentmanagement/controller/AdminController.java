package com.sandhya.studentmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sandhya.studentmanagement.entity.Student;
import com.sandhya.studentmanagement.entity.User;
import com.sandhya.studentmanagement.repository.StudentRepository;
import com.sandhya.studentmanagement.repository.UserRepository;



@Controller
@RequestMapping("/admin/")
public class AdminController {
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private StudentRepository studentRepository;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("title","Admin");
		return "admin/index";
	}


    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll(); 
        model.addAttribute("users", users);
        return "admin/users/index"; 
    }
    
    @GetMapping("/students")
    public String listStudents(Model model) {
        List<Student> students = studentRepository.findAll(); 
        model.addAttribute("students", students);
       
        return "admin/students/index"; 
    }
   
}
