package com.epam.billing.controller;
import com.epam.billing.service.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


@WebServlet(urlPatterns = {"/welcome"})
public class WelcomeServlet extends HttpServlet {
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


}
