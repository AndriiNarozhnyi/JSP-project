package org.itstep.controller.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("username");
        String pass = request.getParameter("password");

        if( name == null || name.equals("") || pass == null || pass.equals("")  ){
            return "/registration.jsp";
        }


        return "redirect:/login.jsp";
    }
}
