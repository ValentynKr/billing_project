package com.epam.billing.controller;

import com.epam.billing.exeption.AppException;
import com.epam.billing.utils.PasswordHashingUtil;
import com.epam.billing.utils.ValidationUtil;
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
                req.getSession().setAttribute("Alert", "User with such email is already registered"); //toDo should be specified by locale
            } else {
                if (ValidationUtil.isPasswordValid(req.getParameter("password"))) {
                    String name = req.getParameter("name");
                    String password = req.getParameter("password");
                    User user = null;
                    try {
                        user = new User().setName(name).setEmail(email).setAdmin(false).setPassword(PasswordHashingUtil.getSaltedHash(password));
                    } catch (AppException e) {
                        e.printStackTrace(); //toDo need to operate the exception
                    }
                    userService.save(user);
                    req.getSession().setAttribute("Alert", "Registration was accomplished. Thank you!");
                } else {
                    req.getSession().setAttribute("Alert", "Chosen pass is invalid. It should contain at least 8 symbols, including number, letter and special symbol");
                }
            }
        } else {
            req.getSession().setAttribute("Alert", "Chosen email is invalid. It should`n contain special symbols or cyrillic letters. Example - ggg@gmail.com");
        }
        req.getRequestDispatcher("/jsp/registration.jsp").forward(req, resp);
    }
}