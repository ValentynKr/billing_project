package com.epam.billing.controller;

import com.epam.billing.entity.User;
import com.epam.billing.exeption.DBException;
import com.epam.billing.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    UserService userService;
    ActivityService activityService;
    ActivityCategoryService activityCategoryService;
    UserActivityService userActivityService;
    UserRequestService userRequestService;

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

        Optional<User> userOptional = null;
        try {
            userOptional = userService.getByEmail(login);
        } catch (DBException e) {
            e.printStackTrace();
        }

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                if (user.isAdmin()) {
                    req.getRequestDispatcher("/jsp/welcome-admin.jsp").forward(req, resp);
                } else {
                    req.getRequestDispatcher("/jsp/welcome.jsp").forward(req, resp);
                }
            } else {
                req.getRequestDispatcher("/jsp/loginErr.jsp").forward(req, resp);
            }
        } else {
            req.getRequestDispatcher("/jsp/loginErr2.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
