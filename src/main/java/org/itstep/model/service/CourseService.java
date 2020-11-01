package org.itstep.model.service;

import org.itstep.model.dao.*;
import org.itstep.model.entity.Course;
import org.itstep.model.entity.User;

import java.util.Map;
import java.util.Optional;

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

    public CoursePage findAllCourses(Pageable pageable, String path, Long userId) {
        CoursePage page;
        try (CourseDao dao = daoFactory.createCourseDao()) {
            page = dao.findAllDispatcher(pageable, path, userId);
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

    public void enrollUser(Long courseId, Long userId) {
        try (CourseDao dao = daoFactory.createCourseDao()) {
            dao.enrollUser(courseId, userId);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void unenrollUser(long courseId, Long userId) {
        try (CourseDao dao = daoFactory.createCourseDao()) {
            dao.unenrollUser(courseId, userId);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Optional<Course> findById(Long courseId) {
        Optional<Course> course = Optional.empty();
        try (CourseDao dao = daoFactory.createCourseDao()) {
            course = dao.findById(courseId);
        }
        return course;
    }

    public void saveEditedCourse(Course course) {
        try (CourseDao dao = daoFactory.createCourseDao()) {
            dao.update(course);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void delete(long courseId) {
        try (CourseDao dao = daoFactory.createCourseDao()) {
            dao.delete(courseId);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
