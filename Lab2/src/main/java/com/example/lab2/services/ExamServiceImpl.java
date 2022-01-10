package com.example.lab2.services;

import com.example.lab2.jms.DataModificationTopic;
import com.example.lab2.jms.EventListenerFactory;
import com.example.lab2.models.Exam;
import com.example.lab2.repositories.ExamRepository;
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
public class ExamServiceImpl implements ExamService {
    private final ExamRepository repository;

    private DataModificationTopic dataModificationTopic;

    @Autowired
    public ExamServiceImpl(ExamRepository repository, EventListenerFactory factory, DataModificationTopic topic) {
        this.repository = repository;
        dataModificationTopic = topic;
        dataModificationTopic.subscribe(factory.createEventLoggerListener());
        dataModificationTopic.subscribe(factory.createEventLoggerListener());
    }

    @Override
    public Optional<Exam> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Exam> findAll() {
        return Converter.iterableToArrayList(repository.findAll());
    }

    @Override
    public void save(Exam exam) {
        repository.save(exam);
        dataModificationTopic.sendInsertEvent("Exam", exam);
    }

    @Override
    public void delete(Exam exam) {
        repository.delete(exam);
        dataModificationTopic.sendDeleteEvent("Exam", exam);
    }
}
