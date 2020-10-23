package org.itstep.controller.Command;

import org.itstep.model.entity.User;
import org.itstep.model.service.CourseService;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CourseSaveCommand implements Command{
    private UserService userService;
    private CourseService courseService;

    public CourseSaveCommand(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<User> teachers = userService.getAllTeachers();
        request.setAttribute("teachers", teachers);
        Map <String, String> paramMap = CommandUtility.refactorParamMap(request.getParameterMap());

        List res = CommandUtility.checkCourseIncorrect(paramMap, request);
        Map <String, String> answerMap = (Map)res.get(0);
        if(!(boolean)res.get(1)){
            paramMap.forEach(request::setAttribute);
            answerMap.forEach(request::setAttribute);
            return "/admin/CourseCreate.jsp";
        }

//        User teacher = userService.findbyId(Long.parseLong(paramMap.get("teacherId")));
//        if(courseService.checkNameDateTeacher(form.get("name"), form.get("startDate"), teacher)){
//            model.addAttribute("CourseNameDateTeacherPresent", messageSource.getMessage("courAlEx", null, locale));
//            model.mergeAttributes((Map)res.get(0));
//            return "AdminCourse";
//        }

        return "/admin/CourseCreate.jsp";
    }
}
