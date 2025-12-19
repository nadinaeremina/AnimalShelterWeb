package org.top.animalshelterwebapp.animal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class Specification {
    private final EntityManager entityManager;

    public Specification(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public List<Animal> findEmployeesByFields(String type, String city, Integer age) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Animal> query = cb.createQuery(Animal.class);
        Root<Animal> root = query.from(Animal.class);

        List<Predicate> predicates = new ArrayList<>();

        if (type != null && !type.isEmpty()) {
            predicates.add(cb.equal(root.get("type").get("title"), type));
        }
        if (city != null && !city.isEmpty()) {
            predicates.add(cb.equal(root.get("city").get("title"), city));
        }
        if (age != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("age"), age));
        }

        if (!predicates.isEmpty()) {
            Predicate finalPredicate = cb.and(predicates.toArray(new Predicate[0]));
            query.where(finalPredicate);
        }

        TypedQuery<Animal> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
