package com.epam.billing.controller;

import com.epam.billing.validation.ValidationUtil;
import com.epam.billing.entity.User;
import com.epam.billing.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/registration"})
public class RegistrationServlet extends HttpServlet {
    private UserService userService;
    private ActivityService activityService;  // toDo delete unused variables after testing
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (ValidationUtil.isEmailValid(req.getParameter("email"))) { //toDo need to be refactored
            String email = req.getParameter("email");
            if (userService.getByEmail(email).isPresent()) {
                req.getSession().setAttribute("Alert", "1");
            } else {
                if (ValidationUtil.isPasswordValid(req.getParameter("password"))) {
                    String name = req.getParameter("name");
                    String password = req.getParameter("password");
                    User user = new User().setName(name).setEmail(email).setAdmin(false).setPassword(password);
                    userService.save(user);
                    req.getSession().setAttribute("Alert", "2");
                } else {
                    req.getSession().setAttribute("Alert", "8");
                }
            }
        } else {
            req.getSession().setAttribute("Alert", "5");
        }
        req.getRequestDispatcher("/jsp/registration.jsp").forward(req, resp);
    }
}