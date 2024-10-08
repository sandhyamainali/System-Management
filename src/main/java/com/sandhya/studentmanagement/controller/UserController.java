package com.sandhya.studentmanagement.controller;

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

import com.sandhya.studentmanagement.entity.User;
import com.sandhya.studentmanagement.enums.Role;
import com.sandhya.studentmanagement.repository.StudentRepository;
import com.sandhya.studentmanagement.repository.UserRepository;
import com.sandhya.studentmanagement.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;

	
	@GetMapping("/")
	public String listUsers(Model model ) {
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);
		return "/admin/users/index";

	}
	@GetMapping("/add")
	public String addUser(Model model) {
		model.addAttribute(new User());
		return "/admin/users/add";

	}
	@PostMapping("/add")
	public String addUser(@ModelAttribute User user) {
		System.out.println(user.getName());
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userService.saveUser(user);
		return "redirect:/admin/users/"; // Redirects to the user list after creation
	}


	@GetMapping("/edit/{id}")
	public String editUserForm(@PathVariable int id, Model model) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setPassword(null);
			model.addAttribute("user", user);
			return "admin/users/edit"; // Returns the edit user form
		}
		return "redirect:/admin/users"; // Redirects to user list if ID not found
	}

	// Handle the update of an existing user
	@PostMapping("/edit/{id}")
	public String updateUserForm(@PathVariable int id,@ModelAttribute User user) {
		Optional<User> existingUser = userRepository.findById(id);
		if (existingUser.isPresent()) {
			User userToUpdate = existingUser.get();
			userToUpdate.setName(user.getName());
			userToUpdate.setEmail(user.getEmail());
			userToUpdate.setUsername(user.getUsername());

			// Only encode and update the password if it was changed
			if (!user.getPassword().isEmpty() && user.getPassword().equals(user.getConfirmPassword()) && !passwordEncoder.matches(user.getPassword(), userToUpdate.getPassword())) {
				String encodedPassword = passwordEncoder.encode(user.getPassword());
				userToUpdate.setPassword(encodedPassword);
			}

			userService.saveUser(userToUpdate); // Save the updated user
		}
		return "redirect:/admin/users/"; // Redirects to the user list after update
	}
	@GetMapping("/delete/{id}")
	public String deletUser(@PathVariable int id, Model model) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			userRepository.delete(user.get());
			model.addAttribute("message", "User Deleted Sucessfully");
		}
		return "redirect:/admin/users"; // Redirects to user list if ID not found
	}
	

}
	
	
	


