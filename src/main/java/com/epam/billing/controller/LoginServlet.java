package com.epam.billing.controller;

import com.epam.billing.entity.User;
import com.epam.billing.exeption.AppException;
import com.epam.billing.DTO.UserActivityUserNameIdDurationRecording;
import com.epam.billing.service.*;
import com.epam.billing.utils.PasswordHashingUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private UserService userService;
    private UserActivityService userActivityService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Optional<User> userOptional = userService.getByEmail(login);
        if (!userOptional.isPresent()) {
            req.getSession().setAttribute("Alert", "User with such email is not registered. Please, register!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            try {
                if (PasswordHashingUtil.check(password, user.getPassword())) {
                    HttpSession session = req.getSession();
                    session.setAttribute("user", user);
                    if (user.isAdmin()) {
                        req.getRequestDispatcher("/jsp/welcome-admin.jsp").forward(req, resp);
                    } else {
                        UserActivityUserNameIdDurationRecording userActivityUserNameIdDurationRecording = new UserActivityUserNameIdDurationRecording();
                        req.getSession().setAttribute("userActivities", userActivityUserNameIdDurationRecording.getUserActivityUserNameIdDurationDTO(user.getUserId()));
                        //   req.getSession().setAttribute("userActivities",
                     //           userActivityService.getUserNameJoin(user.getUserId()));
                        req.getRequestDispatcher("/jsp/welcome.jsp").forward(req, resp);
                    }
                } else {
                    req.getSession().setAttribute("Alert", "Wrong password!");
                    req.getRequestDispatcher("login.jsp").forward(req, resp);
                }
            } catch (AppException e) {
                e.printStackTrace();
            }
        }
    }
}
