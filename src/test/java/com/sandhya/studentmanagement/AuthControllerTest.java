package com.sandhya.studentmanagement;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.sandhya.studentmanagement.controller.AuthController;
import com.sandhya.studentmanagement.entity.User;
import com.sandhya.studentmanagement.service.UserService;



@WebMvcTest(controllers = AuthController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class AuthControllerTest {
	

	
		@Autowired
		private MockMvc mockMvc;

		@MockBean
		private UserService userService;

		@MockBean
		private PasswordEncoder passwordEncoder;

		@Test
		public void testRegisterUser_Success() throws Exception {
			// Mock the behavior of password encoder
			Mockito.when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

			// Perform POST request with valid form data
			mockMvc.perform(post("/register").param("username", "sandhya").param("name", "sandhya")
					.param("email", "sandhya@gmail.com").param("password", "sandhya")
					.param("confirmPassword", "sandhya")).andExpect(status().is3xxRedirection()) // Expect a redirection
																										// status
					.andExpect(redirectedUrl("/login")); // Expect to be redirected to /login

			// Verify that the UserService was called to save the user
			Mockito.verify(userService, Mockito.times(1)).saveUser(Mockito.any(User.class));
		}
		@Test
		public void testRegisterUser_Password() throws Exception {
			// Perform POST request where passwords do not match
			mockMvc.perform(post("/register").param("username", "ram").param("name", "ram")
					.param("email", "ram@gmail.com").param("password", "ram")
					.param("confirmPassword", "ram")).andExpect(status().isOk())

					.andExpect(model().attribute("error", "Passwords do not match."));

		}
}
