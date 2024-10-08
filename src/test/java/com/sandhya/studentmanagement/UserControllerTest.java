//package com.sandhya.studentmanagement;
//
//import static org.hamcrest.CoreMatchers.any;
//import static org.mockito.Mockito.never;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.sandhya.studentmanagement.entity.User;
//import com.sandhya.studentmanagement.repository.UserRepository;
//import com.sandhya.studentmanagement.service.UserService;
//
//public class UserControllerTest {
//	  @Mock
//	    private UserRepository userRepository; 
//
//	    @InjectMocks
//	    private UserService userService; 
//
//	    @BeforeEach
//	    void setUp() {
//	        MockitoAnnotations.openMocks(this);  
//	    }
//
//	    @Test
//	    void testDelete_UserExists() {
//	        // Arrange
//	        int userId = 1;
//	        User user = new User();
//	        user.setId(userId);
//	        when(userRepository.findById(userId)).thenReturn(Optional.of(user)); 
//
//	        // Act
//	        userService.userDelete(userId);
//
//	        // Assert
//	        verify(userRepository, times(1)).findById(userId); 
//	        verify(userRepository, times(1)).save(user); 
////	        assertNotNull(user.getDeletedAt(), "Deleted at should not be null"); 
////	        assertEquals(LocalDate.now(), user.getDeletedAt(), "Deleted at should be today's date");
//	    }
//
//	    @Test
//	    void testDelete_UserDoesNotExist() {
//	        // Arrange
//	        int userId = 1;
//	        when(userRepository.findById(userId)).thenReturn(Optional.empty()); 
//
//	        // Act
//	        userService.Delete(userId);
//
//	        // Assert
//	        verify(userRepository, times(1)).findById(userId);  
//	        verify(userRepository, never()).save(any(User.class));  
//	    }
//
//	    @Test
//	    void testSaveUser() {
//	        // Arrange
//	        User user = new User();
//	        user.setId(1);
//
//	        // Act
//	        userService.saveUser(user);
//
//	        // Assert
//	        verify(userRepository, times(1)).save(user);  
//	    }
//}
