package org.itstep.model.dao;

import org.itstep.model.entity.Course;

import java.sql.SQLException;

public interface CourseDao extends GenericDao<Course>{
    boolean checkNameDateTeacher(String name, String startDate, Long id);
    boolean createCourse(Course course) throws SQLException;
}
