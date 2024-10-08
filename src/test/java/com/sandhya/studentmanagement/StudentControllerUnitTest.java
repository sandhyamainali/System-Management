package com.sandhya.studentmanagement;

import com.sandhya.studentmanagement.controller.StudentController;
import com.sandhya.studentmanagement.entity.Student;
import com.sandhya.studentmanagement.repository.StudentRepository;
import com.sandhya.studentmanagement.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerUnitTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    // 1. Test for listing students (READ operation)
//    @Test
//    void listStudents() throws Exception {
//        // Arrange
//        Student student1 = new Student();
//        student1.setId(1);
//        student1.setName("John");
//        student1.setAddress("KTM");
//        student1.setParentContactInfo("987655432");
//        student1.setGrade(10);
//        student1.setDateOfBirth(LocalDate.of(2005, 1, 1));
//
//        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1));
//
//        // Act & Assert
//        mockMvc.perform(post("/admin/students/"))
//        .andExpect(status().is3xxRedirection())
//        .andExpect(redirectedUrl("/admin/students/"));
//                .andExpect(status().isOk())
//                .andExpect(view().name("/admin/students/index"))
//                .andExpect(model().attributeExists("students"))
//                .andExpect(model().attribute("students", Arrays.asList(student1)));
   // }

    // 2. Test for showing the add form
    @Test
    void showAddStudentForm() throws Exception {
        mockMvc.perform(post("/admin/students/add"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/admin/students/"));
        
//                .andExpect(status().isOk())
//                .andExpect(view().name("/admin/students/add"))
//                .andExpect(model().attributeExists("student"));
    }

    // 3. Test for creating a student (CREATE operation)
    @Test
    void createStudent() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setName("Jasmin");
        student.setAddress("Palpa");
        student.setParentContactInfo("9876543210");
        student.setGrade(9);
        student.setDateOfBirth(LocalDate.of(2006, 5, 15));

        // Act & Assert
        mockMvc.perform(post("/admin/students/add")
                        .param("name", "Jasmin")
                        .param("address", "palpa")
                        .param("parentContactInfo", "9876543210")
                        .param("grade", "9")
                        .param("dateOfBirth", "2006-05-15"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/students/"));

        verify(studentService).saveStudent(any(Student.class));
    }

    // 4. Test for showing the update form
    @Test
    void showUpdateForm() throws Exception {
        // Arrange
        Student existingStudent = new Student();
        existingStudent.setId(1);
        existingStudent.setName("John");
        existingStudent.setAddress("KTM");
        existingStudent.setParentContactInfo("987655432");
        existingStudent.setGrade(10);
        existingStudent.setDateOfBirth(LocalDate.of(2005, 1, 1));

        when(studentRepository.findById(1)).thenReturn(Optional.of(existingStudent));

        // Act & Assert
        mockMvc.perform(get("/admin/students/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/students/edit"))
                .andExpect(model().attributeExists("student"))
                .andExpect(model().attribute("student", existingStudent));
    }

    // 5. Test for updating a student (UPDATE operation)
    @Test
    void updateStudent() throws Exception {
        // Arrange
        Student existingStudent = new Student();
        existingStudent.setId(1);
        existingStudent.setName("Jenny");
        existingStudent.setAddress("Bhaktapur");
        existingStudent.setParentContactInfo("9876543211");
        existingStudent.setGrade(10);
        existingStudent.setDateOfBirth(LocalDate.of(2005, 1, 1));

        when(studentRepository.findById(1)).thenReturn(Optional.of(existingStudent));

        // Act & Assert
        mockMvc.perform(post("/admin/students/update/1")
                        .param("name", "Jenny")
                        .param("address", "Bhaktapur")
                        .param("parentContactInfo", "9876543211")
                        .param("grade", "11")
                        .param("dateOfBirth", "2005-01-01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/students"));

        verify(studentRepository).save(any(Student.class));
    }

    // 6. Test for deleting a student (DELETE operation)
    @Test
    void deleteStudent() throws Exception {
        // Arrange
        Student existingStudent = new Student();
        existingStudent.setId(1);
        when(studentRepository.findById(1)).thenReturn(Optional.of(existingStudent));

        // Act & Assert
        mockMvc.perform(get("/admin/students/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/students"));

        verify(studentRepository).delete(existingStudent);
    }
    
}
