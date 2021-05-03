package org.javaee8.jpa.dynamic.tx;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class EmployeeService {

    @Inject
    private EntityManager entityManager;

    public void persist(Employee employee) {
        entityManager.persist(employee);
    }

    public Employee getById(int id) {
        return entityManager.find(Employee.class, id);
    }
}
