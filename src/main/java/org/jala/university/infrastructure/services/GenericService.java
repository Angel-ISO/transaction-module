package org.jala.university.infrastructure.services;


import org.jala.university.domain.entities.BaseEntity;
import org.jala.university.domain.repository.BaseRepository;
import org.jala.university.infrastructure.interfaces.IGenericService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;


/**
 * Abstract class that implements IGenericService and provides generic CRUD
 * functionalities to be used across various services.
 *
 * This service is designed to work with any entity type that extends BaseEntity.
 * By inheriting this class, specific services can leverage predefined CRUD methods,
 * reducing the need for redundant code in each service.
 *
 * @param <T>  the type of entity the service will handle
 * @param <ID> the type of the entity's identifier
 */


@Service
public abstract class GenericService<T extends BaseEntity, ID extends Serializable> implements IGenericService<T, ID> {

    protected final BaseRepository<T, ID> repository;

    protected GenericService(BaseRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> getById(ID id) {
        return repository.findById(id);
    }

    @Override
    public void create(T entity) {
        repository.save(entity);
    }

    @Override
    public void update(T entity) {
        repository.save(entity);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }
}


