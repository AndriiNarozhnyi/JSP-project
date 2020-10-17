package org.itstep.controller.Command;

import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;
import org.itstep.model.service.InitCoffeeMachine;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command{
    public UserService userService;
    public InitCoffeeMachine initCoffeeMachine;

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        if( name == null || name.equals("") || pass == null || pass.equals("")  ){
            //System.out.println("Not");
            return "/login.jsp";
        }
        System.out.println(name + " " + pass);
        //System.out.println("Yes!");
            //todo: check login with DB

        if(CommandUtility.checkUserIsLogged(request, name)){
            return "/WEB-INF/error.jsp";
        }

        if (name.equals("Admin")){
            CommandUtility.setUserRole(request, Role.ADMIN, name);
            return "/WEB-INF/admin/adminbasis.jsp";
        } else if(name.equals("User")) {
            CommandUtility.setUserRole(request, Role.USER, name);
            return "redirect:/WEB-INF/user/userbasis.jsp";
        } else {
            CommandUtility.setUserRole(request, Role.UNKNOWN, name);
            return "/login.jsp";
        }
    }

}
