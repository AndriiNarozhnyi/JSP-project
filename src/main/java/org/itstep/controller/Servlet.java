package org.itstep.controller;

import org.itstep.controller.Command.*;
import org.itstep.model.service.CourseService;
import org.itstep.model.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class Servlet extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();



    public void init(ServletConfig servletConfig){

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("/logout",
                new LogOutCommand());
        commands.put("/login",
                new LoginCommand(new UserService()));
        commands.put("/exception" , new ExceptionCommand());
        commands.put("/registration", new RegistrationCommand(new UserService()));
        commands.put("/admin/user", new UserListCommand(new UserService()));
        commands.put("/admin/user/edit", new UserEditCommand(new UserService()));
        commands.put("/admin/user/save", new UserSaveCommand(new UserService()));
        commands.put("/admin/user/filter", new UserFilterCommand(new UserService()));
        commands.put("/admin/create", new CourseCreateCommand(new UserService()));
        commands.put("/admin/save", new CourseSaveCommand(new UserService(), new CourseService()));
        commands.put("/user/courses", new CourseListCommand(new CourseService()));
        commands.put("/user/enroll", new UserEnrollCommand(new CourseService()));
        commands.put("/user/unenroll", new UserUnenrollCommand(new CourseService()));
        commands.put("/admin/edit", new CourseEditCommand(new CourseService(), new UserService()));
        commands.put("/admin/predelete", new CoursePredeleteCommand(new CourseService()));
        commands.put("/admin/delete", new CourseDeleteCommand(new CourseService()));
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        Command command = commands.getOrDefault(path ,
                (r,s)->"/index.jsp");
        System.out.println(command.getClass().getName());
        String page = command.execute(request, response);
        if(page.contains("redirect:")){
            response.sendRedirect(page.replace("redirect:", ""));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }

    }
}
