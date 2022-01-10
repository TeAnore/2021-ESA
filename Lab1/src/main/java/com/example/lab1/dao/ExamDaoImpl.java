package com.example.lab1.dao;

import com.example.lab1.models.Exam;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ExamDaoImpl implements ExamDao {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Override
    public Exam get(Integer id) {
        return em.find(Exam.class, id);
    }

    @Override
    public List<Exam> getAll() {
        TypedQuery<Exam> getAllQuery = em.createQuery("select distinct e from Exam e", Exam.class);
        List<Exam> result = getAllQuery.getResultList();
        return result;
    }

    @Override
    public void save(Exam exam) {
        em.persist(exam);
    }

    @Override
    public void update(Exam exam) {
        em.merge(exam);
    }

    @Override
    public void delete(Exam exam) {
        em.remove(em.contains(exam) ? exam : em.merge(exam));
    }
}
