package org.itstep.controller.Command;

import org.itstep.controller.Command.Utility.ValidationAndLocaleUtility;
import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

public class UserEditCommand implements Command {
    private UserService userService;

    public UserEditCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Long userId = Long.parseLong(request.getParameter("userId"));
        User user = userService.findById(userId).orElseThrow(() -> new RuntimeException(
                ValidationAndLocaleUtility.setBundle(request).getString("NoUsrWithId")));
        request.setAttribute("user", user);
        request.setAttribute("roles", Arrays.stream(Role.values())
                .filter(r -> !r.equals(Role.UNKNOWN)).collect(Collectors.toSet()));
        return "/admin/userEdit.jsp";
    }
}
