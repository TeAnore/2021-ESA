package com.example.lab2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Exam")
@Data
public class Exam implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne()
    @JoinColumn(name = "studentId")
    @JsonIgnoreProperties({"exams"})
    private Student student;

    @ManyToOne()
    @JoinColumn(name = "ratingId")
    @JsonIgnoreProperties({"exams"})
    private Rating rating;
}
