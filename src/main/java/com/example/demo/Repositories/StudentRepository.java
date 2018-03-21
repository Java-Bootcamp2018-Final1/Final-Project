package com.example.demo.Repositories;

import com.example.demo.Models.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student,Long> {
    //List<Student> findAllByApprovedProgram(Student stu);
    List<Student> findAllByApprovedProgram(Student student);
}
