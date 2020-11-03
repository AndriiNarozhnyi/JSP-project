package org.itstep.controller.filter;

import org.itstep.model.entity.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();
        HttpSession session = req.getSession();
        ServletContext context = req.getSession().getServletContext();

        if ((path.contains("admin")||path.contains("teacher")||path.contains("user"))
        &&(session.getAttribute("role")==null||session.getAttribute("role").equals(Role.UNKNOWN))){
            response.getWriter().append("AccessDenied");
            return;
        }

        if(path.contains("admin")&&session.getAttribute("role")!=null&&session.getAttribute("role")!=Role.ADMIN){
            response.getWriter().append("AccessDenied");
            return;
        }

        if(path.contains("teacher")&&session.getAttribute("role")!=null&&session.getAttribute("role").equals(Role.USER)){
            response.getWriter().append("AccessDenied");
            return;
        }

        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
