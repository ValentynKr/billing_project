package com.epam.billing.filter;

import com.epam.billing.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class UserFilter implements Filter {

    private final List<String> availableWithoutLogin = asList(
            "/billing_project/login.jsp",
            "/billing_project/login",
            "/billing_project/jsp/registration.jsp",
            "/billing_project/registration",
            "/billing_project/css/signin.css",
            "/billing_project/css/bootstrap.min.css");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        if (availableWithoutLogin.contains(requestURI)) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = ((HttpServletRequest) request).getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/login.jsp");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
