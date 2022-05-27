package com.epam.billing.controller;

import com.epam.billing.entity.User;
import com.epam.billing.repository.UserRepository;
import com.epam.billing.service.ActivityService;
import com.epam.billing.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/welcome"})
public class WelcomeController extends HttpServlet {
    UserService userService;
    ActivityService activityService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("Hello");
        req.getSession().setAttribute("User", userService.getAll());
        req.getRequestDispatcher("/login.jsp").forward(req, resp);


    }
}
