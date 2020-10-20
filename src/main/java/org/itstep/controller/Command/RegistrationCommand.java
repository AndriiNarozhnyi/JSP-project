package org.itstep.controller.Command;

import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashSet;

public class RegistrationCommand implements Command{
    private UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String usernameukr = request.getParameter("usernameukr");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if( username == null || username.equals("") || usernameukr == null || usernameukr.equals("")
              || email == null || email.equals("") ||password == null || password.equals("")  ){
            return "/registration.jsp";
        }
        //TODO check for correct parameters and unique

        userService.saveNewUser(User.builder()
        .username(username)
        .usernameukr(usernameukr)
        .email(email)
        .password(password)
                .active(true)
                .roles(new HashSet<Role>(Collections.singleton(Role.USER)))
        .build());

        return "redirect:/login.jsp";
    }
}
