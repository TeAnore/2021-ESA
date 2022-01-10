package com.example.lab1.controller;

import com.example.lab1.dao.ExamDao;
import com.example.lab1.dao.StudentDao;
import com.example.lab1.dao.RatingDao;
import com.example.lab1.models.Exam;
import com.example.lab1.models.Student;
import com.example.lab1.models.Rating;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@WebServlet
@Path("/exams")
public class ExamController {
    @EJB
    private ExamDao examDao;

    @EJB
    private StudentDao studentDao;

    @EJB
    private RatingDao ratingDao;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("/") // -> localhost:8080/exams
    public Response getExams() throws JsonProcessingException {
        List<Exam> exams = examDao.getAll();
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(exams))
                .build();
    }

    @GET
    @Path("/{examId}") // ->  localhost:8080/student/{examId}
    public Response getExamById(@PathParam("examId") String examId) throws JsonProcessingException {
        Exam exam = examDao.get(Integer.valueOf(examId));

        if (exam == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Exam with id %s not found", examId)).build();

        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(exam))
                .build();
    }

    @POST
    @Path("/")
    public Response addNewExam(
            @FormParam("name") String name,
            @FormParam("studentId") String studentId,
            @FormParam("ratingId") String ratingId) {
        Exam exam = new Exam();
        exam.setName(name);

        Student student = studentDao.get(Integer.valueOf(studentId));
        if (student == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Student with id %s not found", studentId)).build();
        exam.setStudent(student);

        Rating rating = ratingDao.get(Integer.valueOf(ratingId));
        if (rating == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Rating with id %s not found", ratingId)).build();
        exam.setRating(rating);

        examDao.save(exam);
        return Response.ok().build();
    }

    @PUT
    @Path("/{examId}")
    public Response updateExam(
            @PathParam("examId") String examId,
            @DefaultValue("") @FormParam("name") String name,
            @DefaultValue("-1") @FormParam("studentId") String studentId,
            @DefaultValue("-1") @FormParam("ratingId") String ratingId
    ) {
        Exam exam = examDao.get(Integer.valueOf(examId));
        if (exam == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Exam with id %s not found", examId)).build();

        if (!name.isEmpty())
            exam.setName(name);

        if (!studentId.equals("-1")) {
            Student student = studentDao.get(Integer.valueOf(studentId));
            if (student == null)
                return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                        .entity(String.format("Student with id %s not found", studentId)).build();
            exam.setStudent(student);
        }

        if (!ratingId.equals("-1")) {
            Rating rating = ratingDao.get(Integer.valueOf(ratingId));
            if (rating == null)
                return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                        .entity(String.format("Rating with id %s not found", ratingId)).build();
            exam.setRating(rating);
        }

        examDao.update(exam);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{examId}")
    public Response deleteExam(@PathParam("examId") String examId) {
        Exam exam = examDao.get(Integer.valueOf(examId));
        if (exam == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Exam with id %s not found", examId)).build();

        examDao.delete(exam);
        return Response.ok().build();
    }
}
