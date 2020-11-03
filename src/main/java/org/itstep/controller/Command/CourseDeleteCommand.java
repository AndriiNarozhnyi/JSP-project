package org.itstep.controller.Command;

import org.itstep.model.service.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CourseDeleteCommand implements Command {
    private CourseService courseService;

    public CourseDeleteCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        courseService.delete(Long.parseLong(request.getParameter("courseId")));
        return "redirect:/user/courses";
    }
}
