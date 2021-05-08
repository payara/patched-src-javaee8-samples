package org.javaee8.jpa.stream.repository;

import java.util.stream.Stream;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import org.javaee8.jpa.stream.domain.Person;

/**
 * 
 * @author Gaurav Gupta
 *
 */
public class PersonRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Stream<Person> findAll() {
        CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
        criteriaQuery.select(criteriaQuery.from(Person.class));
        return entityManager.createQuery(criteriaQuery).getResultStream();
    }

}
