package org.itstep.model.dao;

import org.itstep.model.entity.Course;

public interface CourseDao extends GenericDao<Course>{
    boolean checkNameDateTeacher(String name, String startDate, Long id);
}
