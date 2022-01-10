package com.example.lab2.controllers;

import com.example.lab2.models.Exam;
import com.example.lab2.models.Student;
import com.example.lab2.models.Rating;
import com.example.lab2.services.ExamService;
import com.example.lab2.services.StudentService;
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
@RequestMapping(value = "/exams")
public class ExamController {
    @Autowired
    private ExamService examService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RatingService ratingService;


    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/json") // localhost:8080/exams
    public ResponseEntity getExams(){
        List<Exam> exams = examService.findAll();
        return ResponseEntity.ok().body(exams);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/xml") // localhost:8080/rating
    public ModelAndView getExamsXSLT() throws JsonProcessingException {
        List<Exam> exams = examService.findAll();
        ModelAndView modelAndView = new ModelAndView("exams");
        Source source = new StreamSource(new ByteArrayInputStream(new XmlMapper().writeValueAsBytes(exams)));
        modelAndView.addObject(source);
        return modelAndView;
    }

    @RequestMapping(value = "/{examId}", method = RequestMethod.GET) // localhost:8080/student
    public ResponseEntity getExamById(@PathVariable("examId") Integer examId){
        Optional<Exam> exam = examService.findById(examId);

        if (!exam.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Exam with id %s not found", examId), HttpStatus.NOT_FOUND);

        return ResponseEntity.ok().body(exam.get());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addNewExam(
            @RequestBody Exam exam,
            @RequestParam Integer studentId,
            @RequestParam Integer ratingId
    ) {
        Optional<Student> student = studentService.findById(studentId);
        if (!student.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Student with id %s not found", studentId), HttpStatus.NOT_FOUND);

        exam.setStudent(student.get());

        Optional<Rating> rating = ratingService.findById(ratingId);
        if (!rating.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Rating with id %s not found", ratingId), HttpStatus.NOT_FOUND);

        exam.setRating(rating.get());

        examService.save(exam);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{examId}", method = RequestMethod.PUT)
    public ResponseEntity updateExam(
            @RequestBody Exam examUpdate,
            @PathVariable("examId") Integer examId,
            @RequestParam(defaultValue = "-1") Integer studentId,
            @RequestParam(defaultValue = "-1") Integer ratingId
    ) {
        Optional<Exam> examWrapper = examService.findById(examId);

        if (!examWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Exam with id %s not found", examId), HttpStatus.NOT_FOUND);

        Exam exam = examWrapper.get();
        if (!examUpdate.getName().isEmpty())
            exam.setName(examUpdate.getName());

        Optional<Student> studentWrapper = studentService.findById(studentId);
        if (!studentWrapper.isPresent() && studentId > 0)
            return new ResponseEntity<Object>(
                    String.format("Student with id %s not found", studentId), HttpStatus.NOT_FOUND);
        exam.setStudent(studentWrapper.get());

        Optional<Rating> ratingWrapper = ratingService.findById(ratingId);
        if (!ratingWrapper.isPresent() && ratingId > 0)
            return new ResponseEntity<Object>(
                    String.format("Rating with id %s not found", ratingId), HttpStatus.NOT_FOUND);
        exam.setRating(ratingWrapper.get());

        examService.save(exam);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{examId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteExam(@PathVariable("examId") Integer examId) {
        Optional<Exam> examWrapper = examService.findById(examId);
        if (!examWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Exam with id %s not found", examId), HttpStatus.NOT_FOUND);

        examService.delete(examWrapper.get());
        return ResponseEntity.ok().build();
    }

}
