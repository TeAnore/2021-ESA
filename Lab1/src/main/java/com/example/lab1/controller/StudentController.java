package com.example.lab1.controller;

import com.example.lab1.dao.StudentDao;
import com.example.lab1.models.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@WebServlet
@Path("/students")
public class StudentController {
    @EJB
    private StudentDao studentDao;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Path("/") // -> localhost:8080/students
    public Response getStudents() throws JsonProcessingException {
        List<Student> students = studentDao.getAll();
        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(students))
                .build();
    }

    @GET
    @Path("/{studentId}") // -> localhost:8080/student/{studentId}
    public Response getStudentById(@PathParam("studentId") String studentId) throws JsonProcessingException {
        Student student = studentDao.get(Integer.valueOf(studentId));

        if (student == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Student with id %s not found", studentId)).build();

        return Response.status(Response.Status.OK.getStatusCode())
                .entity(objectMapper.writeValueAsString(student))
                .build();
    }

    @POST
    @Path("/")
    public Response addNewStudent(
            @FormParam("name") String name,
            @FormParam("email") String email) {
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);

        studentDao.save(student);
        return Response.ok().build();
    }

    @PUT
    @Path("/{studentId}")
    public Response updateStudent(
            @PathParam("studentId") String studentId,
            @DefaultValue("") @FormParam("name") String name,
            @DefaultValue("") @FormParam("email") String email
    ) {
        Student student = studentDao.get(Integer.valueOf(studentId));
        if (student == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Student with id %s not found", studentId)).build();

        if (!name.isEmpty())
            student.setName(name);

        if (!email.isEmpty())
            student.setEmail(email);

        studentDao.update(student);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{studentId}")
    public Response deleteStudent(@PathParam("studentId") String studentId) {
        Student student = studentDao.get(Integer.valueOf(studentId));
        if (student == null)
            return Response.status(Response.Status.NOT_FOUND.getStatusCode())
                    .entity(String.format("Student with id %s not found", studentId)).build();

        studentDao.delete(student);
        return Response.ok().build();
    }
}
