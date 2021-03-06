package org.itstep.controller.Command;

import org.itstep.controller.Command.Utility.LoginUtility;
import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginCommand implements Command {
    public UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        if (name == null || name.equals("") || pass == null || pass.equals("")) {
            return "/login.jsp";
        }

        Optional<User> user = userService.findForLogin(name);

        if (!user.isPresent() || !user.get().getPassword().equals(pass)
                || !user.get().isActive()) {
            return "/login.jsp";
        }

        if (LoginUtility.checkUserIsLogged(request, user.get().getId())) {
            return "/WEB-INF/error.jsp";
        }

        if (user.get().getRoles().contains(Role.ADMIN)) {
            LoginUtility.setUserRole(request, Role.ADMIN, user.get().getId());
            return "redirect:/user/courses";
        } else if (user.get().getRoles().contains(Role.TEACHER)) {
            LoginUtility.setUserRole(request, Role.TEACHER, user.get().getId());
            return "redirect:/teacher/cabinet";
        } else if (user.get().getRoles().contains(Role.USER)) {
            LoginUtility.setUserRole(request, Role.USER, user.get().getId());
            return "redirect:/user/courses";
        } else {
            LoginUtility.setUserRole(request, Role.UNKNOWN, -1L);
            return "/login.jsp";
        }
    }

}
