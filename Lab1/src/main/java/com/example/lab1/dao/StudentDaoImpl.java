package com.example.lab1.dao;

import com.example.lab1.models.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class StudentDaoImpl implements StudentDao {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Override
    public Student get(Integer id) {
        return em.find(Student.class, id);
    }

    @Override
    public List<Student> getAll() {
        TypedQuery<Student> getAllQuery = em.createQuery("select s from Student s", Student.class);
        List<Student> result = getAllQuery.getResultList();
        return result;
    }

    @Override
    public void save(Student student) {
        em.persist(student);
    }

    @Override
    public void update(Student student) {
        em.merge(student);
    }

    @Override
    public void delete(Student student) {
        em.remove(em.contains(student) ? student : em.merge(student));
    }

}
