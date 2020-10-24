package org.itstep.controller.Command;

import org.itstep.model.dao.Pageable;
import org.itstep.model.entity.User;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserListCommand implements Command{
    private UserService userService;
    public UserListCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Pageable pageable = new Pageable();
        List<User> users = userService.findAllUsers(pageable);
        request.setAttribute("users", users);

        return "/admin/userlist.jsp";
    }
}
