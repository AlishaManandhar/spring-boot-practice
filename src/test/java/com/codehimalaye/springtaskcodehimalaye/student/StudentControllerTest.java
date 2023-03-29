package com.codehimalaye.springtaskcodehimalaye.student;

import com.codehimalaye.springtaskcodehimalaye.v1.entity.Student;
import com.codehimalaye.springtaskcodehimalaye.v1.student.StudentController;
import com.codehimalaye.springtaskcodehimalaye.v1.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentRepository studentRepository;

    private List<Student> studentList;

    @BeforeEach
    public void setup() {
        studentList = new ArrayList<>();
        Student student1 = new Student("John Doe", "123 Main St", "S001");
        Student student2 = new Student("Jane Doe", "456 Elm St", "S002");
        studentList.add(student1);
        studentList.add(student2);
    }


    @Test
    void testCreateStudent() {
        Student student = new Student("John Doe", "123 Main St", "S001");
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        ResponseEntity<Student> responseEntity = studentController.createStudent(student);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(student, responseEntity.getBody());
    }

    @Test
    void testGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(studentList);

        ResponseEntity<List<Student>> responseEntity = studentController.getAllStudents();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(studentList, responseEntity.getBody());
    }

    @Test
    void testGetStudentById() {
        Long id = 1L;
        Student student = studentList.get(0);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        ResponseEntity<Student> responseEntity = studentController.getStudentById(id);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(student, responseEntity.getBody());
    }

    @Test
    void testUpdateStudent() {
        Long id = 1L;
        Student student = new Student("John Doe", "123 Main St", "S001");
        Student existingStudent = studentList.get(0);
        existingStudent.setName(student.getName());
        existingStudent.setAddress(student.getAddress());
        existingStudent.setRollNo(student.getRollNo());
        when(studentRepository.findById(id)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenReturn(existingStudent);

        ResponseEntity<Student> responseEntity = studentController.updateStudent(id, student);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(existingStudent, responseEntity.getBody());
    }

    @Test
    void testDeleteStudent() {
        Long id = 1L;
        doNothing().when(studentRepository).deleteById(id);

        ResponseEntity<HttpStatus> responseEntity = studentController.deleteStudent(id);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void testGetStudentByIdNotFound() {
        Long id = 3L;
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Student> responseEntity = studentController.getStudentById(id);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}

