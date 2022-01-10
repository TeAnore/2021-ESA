package com.example.lab2.services;

import com.example.lab2.models.Exam;

import java.util.List;
import java.util.Optional;

public interface ExamService {
    Optional<Exam> findById(Integer id);
    List<Exam> findAll();
    void save(Exam exam);
    void delete(Exam exam);
}
