package org.itstep.controller.Command.Utility;

import org.itstep.model.entity.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class LoginUtility {
    public static void setUserRole(HttpServletRequest request, Role role, Long userId) {
        HttpSession session = request.getSession();
        session.setAttribute("role", role);
        session.setAttribute("userId", userId);
    }

    public static boolean checkUserIsLogged(HttpServletRequest request, Long userId){
        HashSet<Long> loggedUsers = (HashSet<Long>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");

        if(loggedUsers.stream().anyMatch(userId::equals)){
            return true;
        }
        loggedUsers.add(userId);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }
}
