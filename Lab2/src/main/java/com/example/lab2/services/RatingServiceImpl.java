package com.example.lab2.services;

import com.example.lab2.jms.DataModificationTopic;
import com.example.lab2.jms.EventListenerFactory;
import com.example.lab2.models.Rating;
import com.example.lab2.repositories.RatingRepository;
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
public class RatingServiceImpl implements RatingService {
    private final RatingRepository repository;

    private DataModificationTopic dataModificationTopic;

    @Autowired
    public RatingServiceImpl(RatingRepository repository, EventListenerFactory factory) {
        this.repository = repository;
        dataModificationTopic = new DataModificationTopic();
        dataModificationTopic.subscribe(factory.createEmailLoggerListener());
        dataModificationTopic.subscribe(factory.createEventLoggerListener());
    }

    @Override
    public Optional<Rating> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Rating> findAll() {
        return Converter.iterableToArrayList(repository.findAll());
    }

    @Override
    public void save(Rating rating) {
        repository.save(rating);
        dataModificationTopic.sendInsertEvent("Rating", rating);
    }

    @Override
    public void delete(Rating rating) {
        repository.delete(rating);
        dataModificationTopic.sendDeleteEvent("Rating", rating);
    }
}
