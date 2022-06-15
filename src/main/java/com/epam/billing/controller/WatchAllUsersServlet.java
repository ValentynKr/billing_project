package com.epam.billing.controller;

import com.epam.billing.entity.*;
import com.epam.billing.service.ActivityService;
import com.epam.billing.service.UserRequestService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/watchAllUsersServlet"})
public class WatchAllUsersServlet extends HttpServlet {
    private ActivityService activityService;
    private UserRequestService userRequestService;

    @Override
    public void init() {
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        userRequestService = (UserRequestService) getServletContext().getAttribute("userRequestService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
