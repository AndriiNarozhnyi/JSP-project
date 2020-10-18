package org.itstep.controller.Command;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        ServletContext context = session.getServletContext();HashSet<Long> loggedUsers = (HashSet<Long>) request.getSession().getServletContext()
                .getAttribute("loggedUsers");
        loggedUsers.remove(session.getAttribute("userId"));

        session.removeAttribute("userId");
        session.removeAttribute("role");
        context.setAttribute("loggedUsers", loggedUsers);

        return "redirect:/index.jsp";
    }
}
