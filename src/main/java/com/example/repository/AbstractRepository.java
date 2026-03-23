package com.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public class AbstractRepository<T> {

    @PersistenceContext
    EntityManager em;

    private final Class<T> EntityClass;

    protected AbstractRepository(Class<T> EntityClass){
        this.EntityClass = EntityClass;
    }

    public void create(){
        em.persist(EntityClass);
    }

    @Transactional
    public void update(T E){
        em.merge(E);
    }

    public void delete(){
        em.remove(em.contains(EntityClass)?EntityClass:em.merge(EntityClass));
    }

    public T find(UUID id){
        return em.find(EntityClass, id);
    }

    public List<T> findAll(){
        return em.createQuery("select e from "+
                EntityClass.getSimpleName()+" e", EntityClass).getResultList();
    }
}
