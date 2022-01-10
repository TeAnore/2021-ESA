package com.example.lab2.controllers;

import com.example.lab2.models.Rating;
import com.example.lab2.services.RatingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/json") // localhost:8080/ratings
    public ResponseEntity getRatings() {
        List<Rating> ratings = ratingService.findAll();
        return ResponseEntity.ok().body(ratings);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/xml") // localhost:8080/ratings
    public ModelAndView getRatingsXSLT() throws JsonProcessingException {
        List<Rating> ratings = ratingService.findAll();
        ModelAndView modelAndView = new ModelAndView("ratings");
        Source source = new StreamSource(new ByteArrayInputStream(new XmlMapper().writeValueAsBytes(ratings)));
        modelAndView.addObject(source);
        return modelAndView;
    }

    @RequestMapping(value = "/{ratingId}", method = RequestMethod.GET) // localhost:8080/ratings
    public ResponseEntity getRatingById(@PathVariable("ratingId") Integer ratingId) {
        Optional<Rating> rating = ratingService.findById(ratingId);

        if (!rating.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Exam with id %s not found", ratingId), HttpStatus.NOT_FOUND);

        return ResponseEntity.ok().body(rating.get());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addNewRating(
            @RequestBody Rating rating
    ) {
        ratingService.save(rating);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{ratingId}", method = RequestMethod.PUT)
    public ResponseEntity updateRating(
            @RequestBody Rating ratingUpdate,
            @PathVariable("ratingId") Integer ratingId
    ) {
        Optional<Rating> ratingWrapper = ratingService.findById(ratingId);
        if (!ratingWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Exam with id %s not found", ratingId), HttpStatus.NOT_FOUND);

        Rating rating = ratingWrapper.get();
        if (!ratingUpdate.getCode().isEmpty())
            rating.setCode(ratingUpdate.getCode());

        if (!ratingUpdate.getDescription().isEmpty())
            rating.setDescription(ratingUpdate.getDescription());

        ratingService.save(rating);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{ratingId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteRating(@PathVariable("ratingId") Integer ratingId) {
        Optional<Rating> ratingWrapper = ratingService.findById(ratingId);
        if (!ratingWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Rating with id %s not found", ratingId), HttpStatus.NOT_FOUND);

        ratingService.delete(ratingWrapper.get());
        return ResponseEntity.ok().build();
    }
}
