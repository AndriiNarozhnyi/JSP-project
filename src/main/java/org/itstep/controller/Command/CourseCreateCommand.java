package org.itstep.controller.Command;

import org.itstep.model.entity.User;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CourseCreateCommand implements Command{
    private UserService userService;

    public CourseCreateCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<User> teachers = userService.getAllTeachers();
        request.setAttribute("teachers", teachers);
        return "/admin/CourseCreate.jsp";
    }
}
