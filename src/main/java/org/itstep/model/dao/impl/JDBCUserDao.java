package org.itstep.model.dao.impl;

import org.itstep.model.dao.UserDao;
import org.itstep.model.dao.mapper.CourseMapper;
import org.itstep.model.dao.mapper.UserMapper;
import org.itstep.model.entity.Course;
import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;

import java.sql.*;
import java.util.*;

public class JDBCUserDao implements UserDao, SQLConstants{
    private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User user) {
        boolean res1;
        boolean res2 = true;
        long newId;
        try(PreparedStatement ps = connection.prepareStatement(SQL_CREATE_NEW_USER, Statement.RETURN_GENERATED_KEYS)){
            ps.setString( 1, user.getUsername());
            ps.setString( 2, user.getUsernameukr());
            ps.setString( 3, user.getEmail());
            ps.setString( 4, user.getPassword());
            ps.setBoolean( 5, user.isActive());

            ResultSet rs;
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(((Long) rs.getLong(1)));
                    res1 = true;
                }
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        for (Role role : user.getRoles()){
            try(PreparedStatement ps = connection.prepareCall(SQL_ADD_ROLE_FOR_USER)){
                ps.setLong(1, user.getId());
                ps.setString( 2, role.toString());
                res2 = res2&&ps.executeUpdate()>0;
            }catch (Exception ex){
                throw new RuntimeException(ex);
            }
        }

    }

    @Override
    public User findById(int id) {
        return null;
    }

    public Optional<User> findForLogin (String username){
        Map<Long, User> users = new HashMap<>();
        try(PreparedStatement ps = connection.prepareStatement(SQL_FIND_USER_FOR_LOGIN)){
            ps.setString( 1, username);
            ResultSet rs = ps.executeQuery();
            Set<Role> roles = new HashSet<>();
            User user = new User();

            UserMapper userMapper = new UserMapper();

            while (rs.next()) {
                user = userMapper
                        .extractFromResultSet(rs);
                roles.add((userMapper.roleMap.get(rs.getString("role"))));
                user = userMapper
                        .makeUnique(users, user);
                user.getRoles().addAll(roles);
            }
            return Optional.ofNullable(user);


        }catch (Exception ex){
            throw new RuntimeException(ex);
        }



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
