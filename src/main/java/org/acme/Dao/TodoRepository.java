package org.acme.Dao;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.acme.Entity.Todo;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TodoRepository implements PanacheRepository<Todo> {

    @Inject
    EntityManager em;

    public List<Todo> listAll() {
        return findAll().list();
    }

    public Todo findById(UUID id) {
        return find("id", id).firstResult();
    }

    @Transactional
    public void create(Todo todo) {
        em.persist(todo);
    }


    @Transactional
    public void delete(Todo todo) {
        em.remove(todo);
    }

}
