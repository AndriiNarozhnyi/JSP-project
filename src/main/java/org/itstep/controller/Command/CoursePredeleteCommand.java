package org.itstep.controller.Command;

import org.itstep.controller.Command.Utility.ValidationAndLocaleUtility;
import org.itstep.model.entity.Course;
import org.itstep.model.service.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CoursePredeleteCommand implements Command {
    private CourseService courseService;

    public CoursePredeleteCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Course course = courseService.findById(Long.parseLong(request.getParameter("courseId")))
                .orElseThrow(() -> new RuntimeException(
                        ValidationAndLocaleUtility.setBundle(request).getString("NoCourWithId")));
        request.setAttribute("course", course);
        return "/admin/predelete.jsp";
    }
}
