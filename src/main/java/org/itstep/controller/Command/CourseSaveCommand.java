package org.itstep.controller.Command;

import org.itstep.model.entity.Course;
import org.itstep.model.entity.User;
import org.itstep.model.service.CourseService;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;

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
            request.setAttribute("selectedTeacher", paramMap.get("teacherId"));
            return "/admin/CourseCreate.jsp";
        }

        User teacher = userService.findById(Long.parseLong(paramMap.get("teacherId")))
                .orElseThrow(()-> new RuntimeException(CommandUtility.setBundle(request).getString("NoUsrWithId")));
        if(courseService.checkNameDateTeacher(paramMap.get("name"), paramMap.get("startDate"), teacher.getId())){
            request.setAttribute("courAlEx", CommandUtility.setBundle(request).getString("courAlEx"));
            paramMap.forEach(request::setAttribute);
            return "/admin/CourseCreate.jsp";
        }


        courseService.saveNewCourse(Course.builder()
                .name(paramMap.get("name"))
                .nameukr(paramMap.get("nameukr"))
                .topic(paramMap.get("topic"))
                .topicukr(paramMap.get("topicukr"))
                .startDate(LocalDate.parse(paramMap.get("startDate")))
                .duration(DAYS.between(LocalDate.parse(paramMap.get("startDate")), LocalDate.parse(paramMap.get("endDate")).plusDays(1)))
                .endDate(LocalDate.parse(paramMap.get("endDate")))
                .teacher(teacher)
                .build());

        return "/user/courses.jsp";
    }
}
