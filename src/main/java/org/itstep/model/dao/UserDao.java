package org.itstep.model.dao;

import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserDao extends GenericDao<User>{
    Optional<User> findForLogin(String username);
    boolean saveEditedUser(User user);
    UserPage findUsersByFilter(String fusername, String fusernameukr, Pageable pageable);
    List<User> getAllTeachers();

    UserPage findAllPageable(Pageable pageable);
}
