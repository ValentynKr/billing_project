package com.epam.billing.controller;

import com.epam.billing.entity.User;
import com.epam.billing.exeption.DBException;
import com.epam.billing.repository.UserRepository;
import com.epam.billing.service.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/welcome"})
public class WelcomeController extends HttpServlet {
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

        User testUser = new User();
        testUser.setUserId(4).setName("Lola").setAdmin(false).setEmail("bob@gmail.com").setPassword("444");
        try {
            System.out.println(userService.getByEmail(testUser.getEmail()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
