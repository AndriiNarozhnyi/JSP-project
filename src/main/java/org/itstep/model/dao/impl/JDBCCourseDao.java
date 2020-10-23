package org.itstep.model.dao.impl;

import org.itstep.model.dao.CourseDao;
import org.itstep.model.dao.mapper.CourseMapper;
import org.itstep.model.entity.Course;
import org.itstep.model.entity.Role;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class JDBCCourseDao implements CourseDao {
    private Connection connection;
    public JDBCCourseDao(Connection connection) {
        this.connection = connection;
    }


    public void create(Course entity) {
        try(PreparedStatement ps = connection.prepareStatement(SQL_CREATE_NEW_COURSE, Statement.RETURN_GENERATED_KEYS)){
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
                }
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        for (Role role : user.getRoles()){
            try(PreparedStatement ps = connection.prepareCall(SQL_ADD_ROLE_FOR_USER)){
                ps.setLong(1, user.getId());
                ps.setString( 2, role.toString());
            }catch (Exception ex){
                throw new RuntimeException(ex);
            }
        }


    }

    @Override
    public Optional<Course> findById(Long id) {

        return null;
    }



    @Override
    public List<Course> findAll() {
        return null;
    }

    @Override
    public void update(Course entity) {
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

    public Optional<Course> findByName(String name) {

        Optional<Course> result = Optional.empty();
        try(PreparedStatement ps = connection.prepareCall("SELECT * FROM teacher WHERE name = ?")){
            ps.setString( 1, name);
            ResultSet rs;
            rs = ps.executeQuery();
            CourseMapper mapper = new CourseMapper();
            if (rs.next()){
                result = Optional.of(mapper.extractFromResultSet(rs));
            }//TODO : ask question how avoid two teachers with the same name
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public boolean checkNameDateTeacher(String name, String startDate, Long id) {
        return false;
    }
}
