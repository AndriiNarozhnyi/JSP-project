package org.itstep.controller.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;


public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HashSet<Long> loggedUsers = (HashSet<Long>) httpSessionEvent
                .getSession().getServletContext()
                .getAttribute("loggedUsers");
        Long userId = (Long)httpSessionEvent.getSession()
                .getAttribute("userId");
        loggedUsers.remove(userId);
        httpSessionEvent.getSession().setAttribute("loggedUsers", loggedUsers);
    }
}
