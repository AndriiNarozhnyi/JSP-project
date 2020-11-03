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
//    String SQL_FIND_ALL_USERS = "select u.*, r.* from usr u LEFT JOIN role r on u.id=r.usr_id";
    String SQL_FIND_ALL_USERS_PAG = "select u.*, r.* from usr u LEFT JOIN role r on u.id=r.usr_id WHERE u.id IN (?,?)";

    String SQL_USER_TEMPLATE ="select u.*, r.* from usr u LEFT JOIN role r on u.id=r.usr_id WHERE u.id IN ";

    String SQL_UPDATE_USER = "UPDATE usr SET username = ?, usernameukr = ?, email = ?, active = ? WHERE id =?";
    String SQL_DELETE_USER_ROLES = "DELETE FROM role r WHERE r.usr_id =?";

    String SQL_FIND_USERS_BY_FILTER = "select u.*, r.role from usr u LEFT JOIN role r ON u.id=r.usr_id where u.username LIKE ?" +
            "and u.usernameukr like ?";
    String SQL_FIND_ALL_TEACHERS = "select u.*, r.role from usr u JOIN role r ON u.id=r.usr_id " +
            "WHERE u.active='1' AND r.role='TEACHER'";
    String SQL_USER_FOR_PAGE = "select SQL_CALC_FOUND_ROWS id from usr order by id limit ?,?";
    String SQL_USER_FOR_PAGE_FILTER = "select SQL_CALC_FOUND_ROWS id from usr " +
            "where username like ? and usernameukr like ? order by id limit ?,?";





    String SQL_COURSE_TEMPLATE ="select c.*,uhc.usr_id,uhc.course_id,u.* from course c LEFT JOIN usr_has_course uhc " +
            "ON c.id=uhc.course_id INNER JOIN usr u ON c.usr_id=u.id where c.id IN ";
    String SQL_COURSE_FOR_PAGE = "select SQL_CALC_FOUND_ROWS c.id from course c Left join usr_has_course uhc " +
            "ON c.id=uhc.course_id INNER JOIN usr u ON c.usr_id=u.id order by c.id limit ?,?";
    String SQL_CREATE_NEW_COURSE = "insert into course (name, nameukr, topic, topicukr, end_date, duration, start_date, usr_id)" +
            "values (?, ?, ?, ?, ?, ?, ?, ?)";
    String SQL_CHECK_TEACHER = "select count(role) from role where usr_id = ? and role = 'TEACHER'";
    String SQL_SET_TEACHER = "INSERT INTO role (usr_id, role) values (?, 'TEACHER')";
    String SQL_COURSE_FILTER = "select SQL_CALC_FOUND_ROWS c.id from course c left join usr_has_course uhc " +
            "ON c.id = uhc.course_id join usr u ON c.usr_id = u.id where c.name like ? " +
            "and c.nameukr like ? and topic like ? and topicukr like ? and start_date >= ? and duration >= ? " +
            "and duration <= ? and end_date <=? and u.username like ? limit ?,?";
    String SQL_COURSE_FILTER_IP = "select SQL_CALC_FOUND_ROWS c.id from course c left join usr_has_course uhc " +
            "ON c.id = uhc.course_id join usr u ON c.usr_id = u.id where c.name like ? " +
            "and c.nameukr like ? and topic like ? and topicukr like ? and start_date <= ? and duration >= ? " +
            "and duration <= ? and end_date >=? and u.username like ? limit ?,?";
    String SQL_ENROLL_USER = "INSERT INTO usr_has_course (usr_id, course_id) values (?, ?)";
    String SQL_UNENROLL_USER = "delete from usr_has_course where usr_id =? and course_id=?";
    String SQL_FIND_COURSE_BY_ID = "select c.*,uhc.usr_id,uhc.course_id,u.* from course c LEFT JOIN " +
            "usr_has_course uhc ON c.id=uhc.course_id INNER JOIN usr u ON c.usr_id=u.id where c.id = ?";
    String SQL_UPDATE_COURSE = "update course set name=?, nameukr=?, topic=?, topicukr=?, end_date=?," +
            " duration=?, start_date=?, usr_id=? where id=?";
    String SQL_CHECK_NAME_DATE_TEACHER="select count(id) from course where name=? and start_date=? and usr_id =?";
    String SQL_CLEAR_ENROLLMENT = "delete from usr_has_course where course_id=?";
    String SQL_DELETE_COURSE_BY_ID = "delete from course where id=?";






}
