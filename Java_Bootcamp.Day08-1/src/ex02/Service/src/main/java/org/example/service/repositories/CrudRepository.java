package org.example.service.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findById(Long id);
    List<T> findAll();
    void save(T model);
    void delete(Long id);
    void update(T model);
}
