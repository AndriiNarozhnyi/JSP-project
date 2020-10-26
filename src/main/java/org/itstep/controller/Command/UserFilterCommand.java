package org.itstep.controller.Command;

import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserFilterCommand implements Command{
    private UserService userService;

    public UserFilterCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String fusername = request.getParameter("fusername");
        String fusernameukr = request.getParameter("fusernameukr");
//        List<User> users = userService.findUsersByFilter(fusername, fusernameukr);
//        request.setAttribute("fusername", fusername);
//        request.setAttribute("fusernameukr", fusernameukr);
//        request.setAttribute("users", users);
        return "/admin/userlist.jsp";
    }
}
