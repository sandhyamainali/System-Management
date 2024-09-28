package com.sandhya.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sandhya.studentmanagement.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	User findByEmail(String email);

}
