package org.itstep.model.dao.impl;

import org.itstep.model.dao.CourseDao;
import org.itstep.model.dao.mapper.CourseMapper;
import org.itstep.model.entity.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JDBCCourseDao implements CourseDao {
    private Connection connection;

    public JDBCCourseDao(Connection connection) {
        this.connection = connection;
    }


    public void create(Course entity) {
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
}
