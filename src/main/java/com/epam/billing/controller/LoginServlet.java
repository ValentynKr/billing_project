package com.epam.billing.controller;

import com.epam.billing.entity.User;
import com.epam.billing.exeption.AppException;
import com.epam.billing.service.*;
import com.epam.billing.utils.PasswordHashingUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private UserService userService;
    private ActivityService activityService;
    private ActivityCategoryService activityCategoryService;
    private UserActivityService userActivityService;
    private UserRequestService userRequestService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
        userRequestService = (UserRequestService) getServletContext().getAttribute("userRequestService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Optional<User> userOptional = userService.getByEmail(login);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            try {
                if (PasswordHashingUtil.check(password, user.getPassword())) {
                    if (user.isAdmin()) {
                        req.getRequestDispatcher("/jsp/welcome-admin.jsp").forward(req, resp);
                    } else {
                        req.getRequestDispatcher("/jsp/welcome.jsp").forward(req, resp);
                    }
                } else {
                    req.getSession().setAttribute("Alert", "Wrong password!");
                    req.getRequestDispatcher("login.jsp").forward(req, resp);
                }
            } catch (AppException e) {
                e.printStackTrace();
            }
        } else {
            req.getSession().setAttribute("Alert", "User with such email is not registered. Please, register!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
