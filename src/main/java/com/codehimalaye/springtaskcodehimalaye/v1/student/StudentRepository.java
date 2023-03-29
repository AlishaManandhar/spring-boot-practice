package com.codehimalaye.springtaskcodehimalaye.v1.student;

import com.codehimalaye.springtaskcodehimalaye.v1.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRollNo(String rollNo);
}
