package org.itstep.controller.Command;

import org.itstep.model.service.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserEnrollCommand implements Command{
    private CourseService courseService;

    public UserEnrollCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        courseService.enrollUser(Long.parseLong(request.getParameter("courseId")),
                (Long)request.getSession().getAttribute("userId"));
        //todo - log
        return "redirect:" + request.getParameter("path");
    }
}
