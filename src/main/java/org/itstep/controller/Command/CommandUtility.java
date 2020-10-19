package org.itstep.controller.Command;

import org.itstep.model.entity.Role;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;

class CommandUtility {
    static ResourceBundle setBundle(HttpServletRequest request){
        ResourceBundle bundle = ResourceBundle.getBundle("res", new Locale(
                request.getSession().getAttribute("lang").toString()
        ));
        return bundle;
    }
    static void setUserRole(HttpServletRequest request,
                            Role role, Long userId) {
        HttpSession session = request.getSession();
        session.setAttribute("role", role);
        session.setAttribute("userId", userId);
    }

    static boolean checkUserIsLogged(HttpServletRequest request, Long userId){
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
