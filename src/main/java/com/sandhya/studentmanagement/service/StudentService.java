package com.sandhya.studentmanagement.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandhya.studentmanagement.entity.Student;
import com.sandhya.studentmanagement.entity.User;
import com.sandhya.studentmanagement.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Transactional
	public void studentDelete(int id) {
		Optional<Student> optionalStudent = studentRepository.findById(id);
		if(optionalStudent.isPresent()) {
			Student student = optionalStudent.get();
			student.setDeletedAt(LocalDate.now());
			studentRepository.save(student);
		}
	}
	public void saveStudent(Student student) {
		//validate
		// business logic
		// password encode
        studentRepository.save(student); // Save the user in the database
    }
	
	
	
}
