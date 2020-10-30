package org.itstep.controller.Command;

import org.itstep.model.entity.Course;
import org.itstep.model.entity.User;
import org.itstep.model.service.CourseService;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
        request.setAttribute("path", request.getParameter("path"));
        Map <String, String> paramMap = CommandUtility.refactorParamMap(request.getParameterMap());
        Course course = new Course();
        if (request.getParameter("path").equals("/admin/edit")) {
            course = courseService.findById(Long.parseLong(request.getParameter("courseId")))
                    .orElseThrow(() -> new RuntimeException(CommandUtility.setBundle(request).getString("NoCourWithId")));
        }

        List res = CommandUtility.checkCourseIncorrect(paramMap, request, course);
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
            request.setAttribute("selectedTeacher", paramMap.get("teacherId"));
            return "/admin/CourseCreate.jsp";
        }

            course.setName(paramMap.get("name"));
            course.setNameukr(paramMap.get("nameukr"));
            course.setTopic(paramMap.get("topic"));
            course.setTopicukr(paramMap.get("topicukr"));
            course.setStartDate(LocalDate.parse(paramMap.get("startDate")));
            course.setDuration(DAYS.between(LocalDate.parse(paramMap.get("startDate")), LocalDate.parse(paramMap.get("endDate")).plusDays(1)));
            course.setEndDate(LocalDate.parse(paramMap.get("endDate")));
            course.setTeacher(teacher);

        if (request.getParameter("path").equals("/admin/edit")){
            courseService.saveEditedCourse(course);
            return "redirect:/user/courses";
        }

        courseService.saveNewCourse(course);
        return "redirect:/user/courses";
    }
}
