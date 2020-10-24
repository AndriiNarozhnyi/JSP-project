package org.itstep.model.dao.impl;

import org.itstep.model.dao.CourseDao;
import org.itstep.model.dao.Pageable;
import org.itstep.model.dao.mapper.CourseMapper;
import org.itstep.model.entity.Course;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.itstep.model.dao.impl.SQLConstants.*;

public class JDBCCourseDao implements CourseDao {
    private Connection connection;
    public JDBCCourseDao(Connection connection) {
        this.connection = connection;
    }


    public boolean createCourse(Course course) throws SQLException {
        try{
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

                PreparedStatement ps = connection.prepareStatement(SQL_CREATE_NEW_COURSE, Statement.RETURN_GENERATED_KEYS);
            ps.setString( 1, course.getName());
            ps.setString( 2, course.getNameukr());
            ps.setString( 3, course.getTopic());
            ps.setString( 4, course.getTopicukr());
            ps.setDate( 5, Date.valueOf(course.getEndDate()));
            ps.setLong( 6, course.getDuration());
            ps.setDate( 7, Date.valueOf(course.getStartDate()));
            ps.setLong( 8, course.getTeacher().getId());

            ResultSet rs;
            if (ps.executeUpdate() > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    course.setId(((Long) rs.getLong(1)));
                }
            }

        PreparedStatement psc = connection.prepareStatement(SQL_CHECK_TEACHER);
                    psc.setLong(1, course.getTeacher().getId());
                    ResultSet rsc = psc.executeQuery();
                    int count = 0;
            while (rsc.next()) {
                count = rsc.getInt(1);
            }
                    if (count == 0) {
                        PreparedStatement psi = connection.prepareStatement(SQL_SET_TEACHER);
                        psi.setLong(1, course.getTeacher().getId());
                        ResultSet rsi = psi.executeQuery();
                    }
                    connection.commit();

        }
        catch (SQLException ex) {
            // (1) write to log
            connection.rollback();
        }
        return true;
    }

    @Override
    public void create(Course entity) {

    }

    @Override
    public Optional<Course> findById(Long id) {

        return null;
    }



    @Override
    public List<Course> findAll(Pageable pageable) {
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
