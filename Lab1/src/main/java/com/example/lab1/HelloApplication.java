package com.example.lab1;

import com.example.lab1.controller.ExamController;
import com.example.lab1.controller.StudentController;
import com.example.lab1.controller.RatingController;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class HelloApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(StudentController.class);
        classes.add(ExamController.class);
        classes.add(RatingController.class);
        return classes;
    }
}