package org.itstep.model.dao.impl;

public interface SQLConstants {

    String SQL_CREATE_NEW_USER = "INSERT INTO usr (username, usernameukr, email, password, active)"+
            "values (?, ?, ?, ?, ?)";

    String SQL_ADD_ROLE_FOR_USER = "INSERT INTO role (usr_id, role) values (?, ?)";

    String SQL_FIND_USER_FOR_LOGIN = "SELECT u.*, r.role FROM usr u JOIN role r ON u.id=r.usr_id " +
            "WHERE u.username = ?";
    String SQL_FIND_USER_BY_ID = "SELECT u.*, r.role FROM usr u LEFT JOIN role r ON u.id=r.usr_id " +
            "WHERE u.id = ?";

//    String SQL_FIND_ALL_USERS = "select u.*, r.*, c.*, t.* from usr u JOIN role r on u.id=r.usr_id JOIN usr_has_course uhc \n" +
//            "ON u.id=uhc.usr_id JOIN course c ON uhc.course_id=c.id JOIN usr t ON c.usr_id=t.id";
    String SQL_FIND_ALL_USERS = "select u.*, r.* from usr u LEFT JOIN role r on u.id=r.usr_id";

    String SQL_UPDATE_USER = "UPDATE usr SET username = ?, usernameukr = ?, email = ?, active = ? WHERE id =?";
    String SQL_DELETE_USER_ROLES = "DELETE FROM role r WHERE r.usr_id =?";

    String SQL_FIND_USERS_BY_FILTER = "select u.*, r.role from usr u LEFT JOIN role r ON u.id=r.usr_id where u.username LIKE ?" +
            "and u.usernameukr like ?";
    String SQL_FIND_ALL_TEACHERS = "select u.*, r.role from usr u JOIN role r ON u.id=r.usr_id " +
            "WHERE u.active='1' AND r.role='TEACHER'";





    String SQL_CREATE_NEW_COURSE = ""






}
