package org.itstep.model.dao.mapper;

import org.itstep.model.entity.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CourseMapper implements ObjectMapper<Course> {

    @Override
    public Course extractFromResultSet(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setId(rs.getLong("idteacher"));
        course.setName(rs.getString("teacher.name"));
        return course;
    }

    public Course makeUnique(Map<Long, Course> cache,
                              Course course) {
        cache.putIfAbsent(course.getId(), course);
        return cache.get(course.getId());
    }
}
