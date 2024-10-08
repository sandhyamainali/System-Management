package com.sandhya.studentmanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.sandhya.studentmanagement.entity.Student;
import com.sandhya.studentmanagement.enums.Role;
import com.sandhya.studentmanagement.repository.StudentRepository;

import jakarta.transaction.Transactional;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    private Student testStudent;

    @BeforeEach
    void setUp() {
        testStudent = new Student();
        testStudent.setName("Sandhya");
        testStudent.setAddress("KTM");
        testStudent.setParentContactInfo("987654321");
        testStudent.setGrade(11);
        testStudent.setDateOfBirth(LocalDate.of(2005, 1, 1));
        studentRepository.save(testStudent);
    }
    @AfterEach
	public void removeStudent() {
		studentRepository.deleteAll();
	}

    // Test for listing all students
    @Test
    @WithMockUser(username = "sandhya", roles = { "ADMIN" })
    void testStudentList() throws Exception {

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/admin/students/")).andReturn();
		assertEquals(200, result.getResponse().getStatus());
		List<Student> studentList = (List<Student>) result.getModelAndView().getModel().get("students");
		assertNotNull(studentList);
		assertEquals(1, studentList.size());

	}




    // Test for adding a new student
    @Test
    @WithMockUser(username = "sandhya", roles = { "ADMIN" })
    void addStudent() throws Exception {
        mockMvc.perform(post("/admin/students/add")
            .param("name", "Saurab")
            .param("address", "Bhaktapur")
            .param("parentContactInfo", "9876544321")
            .param("grade", "11")
            .param("dateOfBirth", "2004-05-10"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/students/"));
    }

    // Test for editing a student


    // Test for updating an existing student
    @Test
    @WithMockUser(username = "sandhya", roles = { "ADMIN" })
    void updateStudent() throws Exception {
        mockMvc.perform(post("/admin/students/update/1")
            .param("name", "kriti")
            .param("address", "Birtamod")
            .param("parentContactInfo", "9765443210")
            .param("grade", "12")
            .param("dateOfBirth", "2003-07-15"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/students"));
    }

    // Test for deleting a student
    @Test
    @WithMockUser(username = "sandhya", roles = { "ADMIN" })
    void deleteStudent() throws Exception {
        mockMvc.perform(get("/admin/students/delete/" + testStudent.getId()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/students"));

        // Verify the student was marked as deleted (soft delete)
        Optional<Student> deletedStudent = studentRepository.findById(testStudent.getId());
        assert deletedStudent.isEmpty();
       // assert deletedStudent.get().getDeletedAt() != null; // Confirm soft delete was applied
    }

}
