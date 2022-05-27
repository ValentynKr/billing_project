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
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/registration"})
public class RegistrationController extends HttpServlet {
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
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");


            if (userService.getByEmail(email).isPresent()) {
                req.getRequestDispatcher("/jsp/registrationErr.jsp").forward(req, resp);
            } else {
                String name = req.getParameter("name");
                String password = req.getParameter("password");

                User user = new User();
                user.setName(name).setEmail(email).setAdmin(false).setPassword(password);

                userService.save(user);
            }
    }
}
