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
        String path = request.getRequestURI();
        Map<String, String> paramMap = CommandUtility.refactorParamMap(request.getParameterMap());
        Pageable pageable = CommandUtility.makePageable(paramMap);
        List<Object> res = CommandUtility.makeUrlAndCheckFilter(path, paramMap);
        String url = (String) res.get(0);
        boolean filterOff = (boolean)res.get(1);
        CoursePage page = new CoursePage();

        if (filterOff) {
            page = courseService.findAllCourses(pageable, path, (Long)request.getSession().getAttribute("userId"));
        }
        else {
            page = courseService.findCoursesByFilter(paramMap, pageable, path
                    , (Long)request.getSession().getAttribute("userId"));
        }

        request.setAttribute("page", page);
        request.setAttribute("url", url);
        paramMap.forEach((k,v)-> request.setAttribute(k,v));
        request.setAttribute("selectedStatus", paramMap.get("fstatus"));
        request.setAttribute("filterLink", path);

        return "/user/courses.jsp";
    }
}
