package org.itstep.model.dao;

import org.itstep.model.entity.Course;

import java.sql.SQLException;
import java.util.Map;

public interface CourseDao extends GenericDao<Course>{
    boolean checkNameDateTeacher(String name, String startDate, Long id);
    boolean createCourse(Course course) throws SQLException;

    CoursePage findAllPageable(Pageable pageable, String query);

    CoursePage findByFilterDispatcher(Pageable pageable, Map<String, String> paramMap, String menu, Long userId);

    void enrollUser(Long courseId, Long userId);

    void unenrollUser(long courseId, Long userId);

    CoursePage findAllDispatcher(Pageable pageable, String path, Long userId);
}
