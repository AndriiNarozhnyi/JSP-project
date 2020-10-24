package org.itstep.model.service;

import org.itstep.model.dao.CourseDao;
import org.itstep.model.dao.DaoFactory;
import org.itstep.model.entity.Course;

import java.sql.SQLException;

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
}
