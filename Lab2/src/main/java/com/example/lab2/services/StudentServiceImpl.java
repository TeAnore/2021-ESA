package com.example.lab2.services;

import com.example.lab2.jms.DataModificationTopic;
import com.example.lab2.jms.EventListenerFactory;
import com.example.lab2.models.Student;
import com.example.lab2.repositories.StudentRepository;
import com.example.lab2.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Repository
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;

    private DataModificationTopic dataModificationTopic;

    @Autowired
    public StudentServiceImpl(StudentRepository repository, EventListenerFactory factory, DataModificationTopic topic) {
        this.repository = repository;
        dataModificationTopic = topic;
        dataModificationTopic.subscribe(factory.createEmailLoggerListener());
        dataModificationTopic.subscribe(factory.createEventLoggerListener());
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return Converter.iterableToArrayList(repository.findAll());
    }

    @Override
    public void save(Student student) {
        repository.save(student);
        dataModificationTopic.sendInsertEvent("Student", student);
    }

    @Override
    public void delete(Student student) {
        repository.delete(student);
        dataModificationTopic.sendDeleteEvent("Student", student);
    }

    @Override
    public List<Student> findByName(String name) {
        return repository.findByName(name);
    }
}
