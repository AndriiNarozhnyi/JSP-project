package org.itstep.model.service;

import org.itstep.model.dao.*;
import org.itstep.model.entity.Course;

import java.util.Map;

public class CourseService {
    DaoFactory daoFactory = DaoFactory.getInstance();
    public boolean checkNameDateTeacher(String name, String startDate, Long id) {
        boolean res = false;
        try (CourseDao dao = daoFactory.createCourseDao()) {
            res = dao.checkNameDateTeacher(name, startDate, id);
        }
        return res;
    }

    public void saveNewCourse(Course course) {
        try (CourseDao dao = daoFactory.createCourseDao()) {
            dao.createCourse(course);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public CoursePage findAllCourses(Pageable pageable) {
        CoursePage page;
        try (CourseDao dao = daoFactory.createCourseDao()) {
            page = dao.findAllPageable(pageable);
        }
        return page;
    }

    public CoursePage findCoursesByFilter(Map<String, String> paramMap, Pageable pageable, String menu, Long userId) {
        CoursePage page;
        try (CourseDao dao = daoFactory.createCourseDao()) {
            page = dao.findByFilterDispatcher(pageable, paramMap, menu, userId);
        }
        return page;

    }
}
