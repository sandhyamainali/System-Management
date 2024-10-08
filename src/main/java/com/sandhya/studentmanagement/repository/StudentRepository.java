package com.sandhya.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sandhya.studentmanagement.entity.Student;
import com.sandhya.studentmanagement.entity.User;

public interface StudentRepository extends JpaRepository<Student,Integer> {

	void save(User user);

}
