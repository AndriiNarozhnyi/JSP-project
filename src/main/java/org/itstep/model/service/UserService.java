package org.itstep.model.service;

import org.itstep.model.dao.DaoFactory;
import org.itstep.model.dao.Pageable;
import org.itstep.model.dao.UserDao;
import org.itstep.model.dao.UserPage;
import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;

import java.util.*;

public class UserService {
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

    public UserPage findAllUsers(Pageable pageable) {
        UserPage page;
        try (UserDao dao = daoFactory.createUserDao()) {
            page = dao.findAllPageable(pageable);
        }
        return page;
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

    public UserPage findUsersByFilter(String fusername, String fusernameukr, Pageable pageable) {
        UserPage page = new UserPage();
        try (UserDao dao = daoFactory.createUserDao()) {
            page = dao.findUsersByFilter(fusername, fusernameukr, pageable);
        }
        return page;
    }

    public List<User> getAllTeachers() {
        List<User> users;
        try (UserDao dao = daoFactory.createUserDao()) {
            users = dao.getAllTeachers();
        }
        return users;
    }
}
