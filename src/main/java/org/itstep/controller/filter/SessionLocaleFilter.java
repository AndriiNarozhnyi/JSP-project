package org.itstep.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SessionLocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

            HttpServletRequest req = (HttpServletRequest) request;
            if (req.getSession().getAttribute("lang")==null){
                req.getSession().setAttribute("lang", "en");
            }

            if (req.getParameter("sessionLocale") != null) {
                req.getSession().setAttribute("lang", req.getParameter("sessionLocale"));
            }
            chain.doFilter(request, response);

    }

    @Override
    public void destroy() {}

}
