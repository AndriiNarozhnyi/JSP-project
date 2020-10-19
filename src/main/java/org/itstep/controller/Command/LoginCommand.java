package org.itstep.controller.Command;

import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginCommand implements Command{
    public UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        if( name == null || name.equals("") || pass == null || pass.equals("")  ){
            return "/login.jsp";
        }
        System.out.println(name + " " + pass);

        Optional<User> user = Optional.ofNullable(UserService.users.get(name));

        if(!user.isPresent()||!user.get().getPassword().equals(pass)){
            //TODO add check for "isActive"
            return "/login.jsp";
        }

        if(CommandUtility.checkUserIsLogged(request, user.get().getId())){
            return "/WEB-INF/error.jsp";
        }

        if (user.get().getRoles().contains(Role.ADMIN)){
            CommandUtility.setUserRole(request, Role.ADMIN, user.get().getId());
            return "redirect:/admin/adminbasis.jsp";
        } else if(user.get().getRoles().contains(Role.USER)) {
            CommandUtility.setUserRole(request, Role.USER, user.get().getId());
            ResourceBundle bundle = CommandUtility.setBundle(request);
            request.setAttribute("message1", bundle.getString("incEndDate"));
            return "/user/userbasis.jsp";
        } else if(user.get().getRoles().contains(Role.TEACHER)) {
            CommandUtility.setUserRole(request, Role.TEACHER, user.get().getId());
            return "redirect:/teacher/teacherbasis.jsp";
        } else {
            CommandUtility.setUserRole(request, Role.UNKNOWN, -1L);
            return "/login.jsp";
        }
    }

}
