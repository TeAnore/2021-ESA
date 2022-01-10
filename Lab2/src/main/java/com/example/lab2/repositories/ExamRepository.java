package com.example.lab2.repositories;

import com.example.lab2.models.Exam;
import org.springframework.data.repository.CrudRepository;

public interface ExamRepository extends CrudRepository<Exam, Integer> {
}
