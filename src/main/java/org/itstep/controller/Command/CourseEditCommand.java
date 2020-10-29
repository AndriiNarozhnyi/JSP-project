package org.itstep.controller.Command;

import org.itstep.model.service.CourseService;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CourseEditCommand implements Command{
    private CourseService courseService;
    private UserService userService;

    public CourseEditCommand(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
