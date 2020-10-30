package org.itstep.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable {
    void create (T entity);
    Optional<T> findById(Long id);
    List<T> findAll(Pageable pageable);
    void update(T entity) throws SQLException;
    void delete(long id);
    void close();
}
