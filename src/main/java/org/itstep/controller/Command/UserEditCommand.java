package org.itstep.controller.Command;

import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserEditCommand implements Command{
    private UserService userService;
    public UserEditCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Long userId = Long.parseLong(request.getParameter("userId"));
        User user = userService.findById(userId).orElseThrow(()-> new RuntimeException(
                CommandUtility.setBundle(request).getString("NoUsrWithId")));
        request.setAttribute("user", user);
//        request.setAttribute("roles", Arrays.stream(Role.values())
//                .collect(Collectors.toSet()).removeIf(r -> r.equals(Role.UNKNOWN)));
        request.setAttribute("roles", Arrays.stream(Role.values())
                .filter(r->!r.equals(Role.UNKNOWN)).collect(Collectors.toSet()));
        System.out.println(user.getRoles().contains(Role.ADMIN));
        Set<String> rolest = new HashSet<>();
        for (Role role: user.getRoles()){
            rolest.add(role.toString());
        }
        request.setAttribute("rolest", rolest);
        System.out.println(user.getRoles());
        System.out.println(user.getRoles());
        return "/admin/userEdit.jsp";
    }
}
