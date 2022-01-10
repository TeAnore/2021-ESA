package com.example.lab2.services;

import com.example.lab2.models.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Optional<Student> findById(Integer id);
    List<Student> findAll();
    void save(Student student);
    void delete(Student student);
    List<Student> findByName(String name);

}
