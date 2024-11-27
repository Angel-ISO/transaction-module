package org.jala.university.infrastructure.interfaces;


import org.jala.university.domain.entities.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


/**
 * Interface for generic CRUD operations on a service for a specific type.
 *
 * This interface provides a layer of abstraction for commonly used CRUD operations.
 * Implementing this interface allows for easy reuse and reduces code duplication
 * in services that handle different entities.
 *
 * @param <T>  the type of the entity to handle
 * @param <ID> the type of the entity's identifier
 */



public interface IGenericService<T extends BaseEntity, ID extends Serializable> {

    public List<T> getAll();

    public Optional<T> getById(ID id);

    public void create(T entity);

    public void update(T entity);

    public void delete(ID id);

}

