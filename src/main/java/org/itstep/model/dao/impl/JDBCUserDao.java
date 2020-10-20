package org.itstep.model.dao.impl;

import org.itstep.model.dao.UserDao;
import org.itstep.model.dao.mapper.CourseMapper;
import org.itstep.model.dao.mapper.UserMapper;
import org.itstep.model.entity.Course;
import org.itstep.model.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUserDao implements UserDao {
    private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User entity) {

    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        Map<Long, User> users = new HashMap<>();
        Map<Long, Course> courses = new HashMap<>();

        final String query = "" +
                " select * from usr" +
                " left join usr_has_course using (id_usr)" +
                " left join course using (id_course)";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);

            CourseMapper courseMapper = new CourseMapper();
            UserMapper userMapper = new UserMapper();

            while (rs.next()) {
                User user = userMapper
                        .extractFromResultSet(rs);
                Course course = courseMapper
                        .extractFromResultSet(rs);
                user = userMapper
                        .makeUnique(users, user);
                course = courseMapper
                        .makeUnique(courses, course);
                user.getTakenCourses().add(course);
            }
            return new ArrayList<>(users.values());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(User entity) {
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
