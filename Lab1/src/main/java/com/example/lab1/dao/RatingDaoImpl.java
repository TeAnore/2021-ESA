package com.example.lab1.dao;

import com.example.lab1.models.Rating;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Stateless
public class RatingDaoImpl implements RatingDao {

    @PersistenceContext(unitName = "default")
    private EntityManager em;

    @Override
    public Rating get(Integer id) {
        return em.find(Rating.class, id);
    }

    @Override
    public List<Rating> getAll() {
        TypedQuery<Rating> getAllQuery = em.createQuery("select r from Rating r", Rating.class);
        List<Rating> result = getAllQuery.getResultList();
        return result;
    }

    @Override
    public void save(Rating rating) {
        em.persist(rating);
    }

    @Override
    public void update(Rating rating) {
        em.merge(rating);
    }

    @Override
    public void delete(Rating rating) {
        em.remove(em.contains(rating) ? rating : em.merge(rating));
    }
}
