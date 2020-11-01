package org.itstep.controller.Command;

import org.itstep.model.entity.Role;
import org.itstep.model.entity.User;
import org.itstep.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class RegistrationCommand implements Command{
    private UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameterMap().size()==0){
            return "registration.jsp";
        }

        Map<String, String> form = CommandUtility.refactorParamMap(request.getParameterMap());

        List<Object> res = CommandUtility.checkUserIncorrect(form, request);
        boolean userIncorrect = (boolean)res.get(1);
        if(userIncorrect){
            Map<String, String> answer = (Map<String, String>) res.get(0);
            form.forEach((k,v)->request.setAttribute(k,v));
            answer.forEach((k,v)->request.setAttribute(k,v));
            return "registration.jsp";
        }
        try {
            userService.saveNewUser(User.builder()
                    .username(form.get("username"))
                    .usernameukr(form.get("usernameukr"))
                    .email(form.get("email"))
                    .password(form.get("password"))
                    .active(true)
                    .roles(new HashSet<Role>(Collections.singleton(Role.USER)))
                    .build());
        } catch (Exception e){
            e.printStackTrace();
            form.forEach((k,v)->request.setAttribute(k,v));
            request.setAttribute("messageUserPresent", CommandUtility.setBundle(request).
                    getString("messageUserPresent"));
            return "registration.jsp";
        }

        return "redirect:/login.jsp";
    }
}
