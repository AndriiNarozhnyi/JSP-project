package org.itstep.controller.Command;

import org.itstep.model.service.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserUnenrollCommand implements Command{
    private CourseService courseService;

    public UserUnenrollCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        courseService.unenrollUser(Long.parseLong(request.getParameter("courseId")),
                (Long)request.getSession().getAttribute("userId"));
        //todo - log
        return "redirect:" + request.getParameter("path");
    }
}
