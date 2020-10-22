package org.itstep.controller.Command;

import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSaveCommand implements Command{
    private UserService userService;
    public UserSaveCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> paramMap = request.getParameterMap();
        //todo - validate incoming user data
        Long userId = Long.parseLong(request.getParameter("userId"));
        User user = userService.findById(userId).orElseThrow(()-> new RuntimeException(
                CommandUtility.setBundle(request).getString("NoUsrWithId")));
        user.setUsername(request.getParameter("username"));
        user.setUsernameukr(request.getParameter("usernameukr"));
        user.setEmail(request.getParameter("email"));

        if (request.getParameter("isActive")!=null) {
            user.setActive(true);
        } else {
            user.setActive(false);
        }

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();

        for (String key : paramMap.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userService.saveEditedUser(user);

    return "redirect:/admin/user";
    }
}
