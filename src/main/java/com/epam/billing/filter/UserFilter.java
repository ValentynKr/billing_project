package com.epam.billing.filter;

import com.epam.billing.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/jsp/welcome.jsp")
public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
//            session.setAttribute("Alert", "Please, sign in");             <---- this shit doesn`t word
            ((HttpServletResponse) response).sendRedirect("../login.jsp");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
