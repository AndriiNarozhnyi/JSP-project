package org.itstep.model.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable {
    void create (T entity);
    Optional<T> findById(Long id);
    List<T> findAll(Pageable pageable);
    void update(T entity);
    void delete(int id);
    void close();
}
