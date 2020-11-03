package org.itstep.controller.Command;

import org.itstep.controller.Command.Utility.ValidationAndLocaleUtility;
import org.itstep.model.entity.Course;
import org.itstep.model.entity.User;
import org.itstep.model.service.CourseService;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CourseEditCommand implements Command {
    private CourseService courseService;
    private UserService userService;

    public CourseEditCommand(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<User> teachers = userService.getAllTeachers();
        Course course = courseService.findById(Long.parseLong(request.getParameter("courseId")))
                .orElseThrow(() -> new RuntimeException(
                        ValidationAndLocaleUtility.setBundle(request).getString("NoCourWithId")));

        request.setAttribute("courseId", course.getId());
        request.setAttribute("name", course.getName());
        request.setAttribute("nameukr", course.getNameukr());
        request.setAttribute("topic", course.getTopic());
        request.setAttribute("topicukr", course.getTopicukr());
        request.setAttribute("startDate", course.getStartDate());
        request.setAttribute("endDate", course.getEndDate());
        request.setAttribute("selectedTeacher", course.getTeacher().getId());
        request.setAttribute("teachers", teachers);
        request.setAttribute("path", "/admin/edit");
        request.setAttribute("course", course);
        return "/admin/CourseCreate.jsp";
    }
}
