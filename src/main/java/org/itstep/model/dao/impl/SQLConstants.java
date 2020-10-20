package org.itstep.model.dao.impl;

public interface SQLConstants {

    String SQL_CREATE_NEW_USER = "INSERT INTO usr (username, usernameukr, email, password, active)"+
            "values (?, ?, ?, ?, ?)";

    String SQL_ADD_ROLE_FOR_USER = "INSERT INTO role (usr_id, role) values (?, ?)";

    String SQL_FIND_USER_FOR_LOGIN = "SELECT u.*, r.role FROM usr u JOIN role r ON u.id=r.usr_id " +
            "WHERE u.username = ?";





}
