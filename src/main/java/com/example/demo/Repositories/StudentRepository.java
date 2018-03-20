package com.example.demo.Repositories;

import com.example.demo.Models.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student,Long> {
}
