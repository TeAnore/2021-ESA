package com.example.lab2.repositories;

import com.example.lab2.models.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    List<Student> findByName(String name);
}
