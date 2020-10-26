package org.itstep.controller.Command;

import org.itstep.model.dao.CoursePage;
import org.itstep.model.dao.Pageable;
import org.itstep.model.service.CourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class CourseListCommand implements Command{
    private CourseService courseService;

    public CourseListCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String path = "/user/courses";
        Map<String, String> paramMap = CommandUtility.refactorParamMap(request.getParameterMap());
        Pageable pageable = CommandUtility.makePageable(paramMap);
        List<Object> res = CommandUtility.makeUrlAndCheckFilter(path, paramMap);
        String url = (String) res.get(0);
        CoursePage page = new CoursePage();

        if ((boolean)res.get(1)) {
            page = courseService.findAllCourses(pageable);
        } else {
            page = courseService.findCoursesByFilter(paramMap);
        }

        request.setAttribute("page", page);
        request.setAttribute("url", url);
        paramMap.forEach((k,v)-> request.setAttribute(k,v));

        return "/user/courses.jsp";
    }
}
