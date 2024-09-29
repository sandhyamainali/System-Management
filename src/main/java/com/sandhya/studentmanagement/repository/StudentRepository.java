package com.sandhya.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sandhya.studentmanagement.entity.Student;

public interface StudentRepository extends JpaRepository<Student,Integer> {

}
