package org.itstep.model.dao;

import org.itstep.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User>{
    Optional<User> findForLogin(String username);
}
