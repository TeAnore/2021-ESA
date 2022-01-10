package com.example.lab2.controllers;

import com.example.lab2.models.Student;
import com.example.lab2.services.StudentService;
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
@RequestMapping(value = "/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/json") // localhost:8080/students
    public ResponseEntity getStudents() {
        List<Student> students = studentService.findAll();
        return ResponseEntity.ok().body(students);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, headers="accept=application/xml") // localhost:8080/students
    public ModelAndView getStudentsXSLT() throws JsonProcessingException {
        List<Student> students = studentService.findAll();
        ModelAndView modelAndView = new ModelAndView("students");
        Source source = new StreamSource(new ByteArrayInputStream(new XmlMapper().writeValueAsBytes(students)));
        modelAndView.addObject(source);
        return modelAndView;
    }

    @RequestMapping(value = "/{studentId}", method = RequestMethod.GET) // localhost:8080/students
    public ResponseEntity getStudentById(@PathVariable("studentId") Integer studentId) {
        Optional<Student> student = studentService.findById(studentId);

        if (!student.isPresent())
            return new ResponseEntity<Object>(String.format("Student with id %s not found", studentId), HttpStatus.NOT_FOUND);

        return ResponseEntity.ok().body(student.get());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity addNewStudent(@RequestBody Student student) {
        studentService.save(student);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{studentId}", method = RequestMethod.PUT)
    public ResponseEntity updateStudent(
            @RequestBody Student studentUpdate,
            @PathVariable("studentId") Integer studentId
    ) {
        Optional<Student> studentWrapper = studentService.findById(studentId);
        if (!studentWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Student with id %s not found", studentId), HttpStatus.NOT_FOUND);

        Student student = studentWrapper.get();
        if (!studentUpdate.getEmail().isEmpty())
            student.setEmail(studentUpdate.getEmail());

        if (!studentUpdate.getName().isEmpty())
            student.setName(studentUpdate.getName());

        studentService.save(student);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{studentId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteStudent(@PathVariable("studentId") Integer studentId) {
        Optional<Student> studentWrapper = studentService.findById(studentId);
        if (!studentWrapper.isPresent())
            return new ResponseEntity<Object>(
                    String.format("Student with id %s not found", studentId), HttpStatus.NOT_FOUND);

        studentService.delete(studentWrapper.get());
        return ResponseEntity.ok().build();
    }
}
