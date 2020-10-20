package org.itstep.controller.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command{
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



        return "redirect:/login.jsp";
    }
}
