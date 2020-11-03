package org.itstep.controller.Command;

import org.itstep.controller.Command.Utility.PaginationAndParamMapUtility;
import org.itstep.controller.Command.Utility.ValidationAndLocaleUtility;
import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

public class UserSaveCommand implements Command {
    private UserService userService;

    public UserSaveCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> form = PaginationAndParamMapUtility.refactorParamMap(request.getParameterMap());
        List<Object> res = ValidationAndLocaleUtility.checkUserIncorrect(form, request);
        boolean userIncorrect = (boolean) res.get(1);
        if (userIncorrect) {
            Map<String, String> answer = (Map<String, String>) res.get(0);
            form.forEach((k, v) -> request.setAttribute(k, v));
            answer.forEach((k, v) -> request.setAttribute(k, v));
            Long userId = Long.parseLong(request.getParameter("userId"));
            User user = userService.findById(userId).orElseThrow(() -> new RuntimeException(
                    ValidationAndLocaleUtility.setBundle(request).getString("NoUsrWithId")));
            request.setAttribute("user", user);
            request.setAttribute("roles", Arrays.stream(Role.values())
                    .filter(r -> !r.equals(Role.UNKNOWN)).collect(Collectors.toSet()));
            return "/admin/userEdit.jsp";
        }
        Long userId = Long.parseLong(request.getParameter("userId"));
        User user = userService.findById(userId).orElseThrow(() -> new RuntimeException(
                ValidationAndLocaleUtility.setBundle(request).getString("NoUsrWithId")));
        user.setUsername(request.getParameter("username"));
        user.setUsernameukr(request.getParameter("usernameukr"));
        user.setEmail(request.getParameter("email"));

        if (request.getParameter("isActive") != null) {
            user.setActive(true);
        } else {
            user.setActive(false);
        }

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        try {
            userService.saveEditedUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            form.forEach((k, v) -> request.setAttribute(k, v));
            request.setAttribute("messageUserPresent", ValidationAndLocaleUtility.setBundle(request).
                    getString("messageUserPresent"));
            request.setAttribute("user", user);
            request.setAttribute("roles", Arrays.stream(Role.values())
                    .filter(r -> !r.equals(Role.UNKNOWN)).collect(Collectors.toSet()));
            return "/admin/userEdit.jsp";
        }

        return "redirect:/admin/user";
    }
}
