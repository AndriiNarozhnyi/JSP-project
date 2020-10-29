package org.itstep.model.dao.mapper;

import org.itstep.model.entity.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class CourseMapper implements ObjectMapper<Course> {

    @Override
    public Course extractFromResultSet(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setId(rs.getLong(1));
        course.setName(rs.getString(2));
        course.setNameukr(rs.getString(3));
        course.setTopic(rs.getString(4));
        course.setTopicukr(rs.getString(5));
        course.setEndDate(rs.getDate(6).toLocalDate());
        course.setStartDate(rs.getDate(7).toLocalDate());
        course.setDuration(rs.getLong(8));
        return course;
    }

    public Course makeUnique(Map<Long, Course> cache,
                              Course course) {
        cache.putIfAbsent(course.getId(), course);
        return cache.get(course.getId());
    }
}
