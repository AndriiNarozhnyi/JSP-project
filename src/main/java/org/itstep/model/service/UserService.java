package org.itstep.model.service;

import org.itstep.model.dao.DaoFactory;
import org.itstep.model.dao.UserDao;
import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;

import java.util.*;

public class UserService {
    public static Map<String, User> usersReserve = new HashMap<>();
    DaoFactory daoFactory = DaoFactory.getInstance();

    public UserService() {
    }

    public boolean saveNewUser (User user){
        try (UserDao dao = daoFactory.createUserDao()) {
           dao.create(user);
        }
        return true;
    }
    public Optional<User> findForLogin (String username){
        Optional res = Optional.empty();
        try (UserDao dao = daoFactory.createUserDao()) {
            res = dao.findForLogin(username);
        }
        return res;
    }

    public List<User> findAllUsers() {
        List<User> users;
        try (UserDao dao = daoFactory.createUserDao()) {
            users = dao.findAll();
        }
        return users;
    }

    public Optional<User> findById(Long userId) {
        Optional<User> user = Optional.empty();
        try (UserDao dao = daoFactory.createUserDao()) {
            user = dao.findById(userId);
        }
        return user;
    }

    public boolean saveEditedUser(User user) {
        boolean res = false;
        try (UserDao dao = daoFactory.createUserDao()) {
            res = dao.saveEditedUser(user);
        }
        return res;
    }

    public List<User> findUsersByFilter(String fusername, String fusernameukr) {
        List<User> users;
        try (UserDao dao = daoFactory.createUserDao()) {
            users = dao.findUsersByFilter(fusername, fusernameukr);
        }
        return users;
    }
}
