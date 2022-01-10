package com.example.lab2.services;

import com.example.lab2.models.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    Optional<Rating> findById(Integer id);
    List<Rating> findAll();
    void save(Rating rating);
    void delete(Rating rating);
}
