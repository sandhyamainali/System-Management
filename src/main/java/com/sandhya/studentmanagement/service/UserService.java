package com.sandhya.studentmanagement.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandhya.studentmanagement.entity.User;
import com.sandhya.studentmanagement.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
	private  UserRepository userRepository;
	
	@Transactional
	public void studentDelete(int id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setDeletedAt(LocalDate.now());
			userRepository.save(user);
		}		
	}
	
	public void saveUser(User user) {
        userRepository.save(user); // Save the user in the database
    }
	
}
