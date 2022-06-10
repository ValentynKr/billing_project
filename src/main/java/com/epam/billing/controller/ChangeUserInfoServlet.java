package com.epam.billing.controller;

import com.epam.billing.entity.User;
import com.epam.billing.service.*;
import com.epam.billing.utils.PasswordHashingUtil;
import com.epam.billing.utils.ValidationUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/changeUserInfo"})
public class ChangeUserInfoServlet extends HttpServlet {
    private static final String EMAIL_IS_INVALID_MESSAGE = "Chosen email is invalid. It should`n contain special symbols or cyrillic letters. Example - ggg@gmail.com";
    private static final String PASSWORD_IS_INVALID_MESSAGE = "Chosen pass is invalid. It should contain at least 8 symbols, including number, letter and special symbol";
    private UserService userService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        String newPassword = req.getParameter("password");
        String currentPassword = user.getPassword();
        if (newPassword.isEmpty()) {
            if (ValidationUtil.isEmailValid(req.getParameter("email"))) {
                updateUser(req, user, currentPassword);
            } else {
                req.getSession().setAttribute("Alert", EMAIL_IS_INVALID_MESSAGE);
            }
        } else {
            if (ValidationUtil.isEmailValid(req.getParameter("email"))) {
                if (ValidationUtil.isPasswordValid(req.getParameter("password"))) {
                    updateUser(req, user, PasswordHashingUtil.getSaltedHash(currentPassword));
                } else {
                    req.getSession().setAttribute("Alert", PASSWORD_IS_INVALID_MESSAGE);
                }
            } else {
                req.getSession().setAttribute("Alert", EMAIL_IS_INVALID_MESSAGE);
            }
        }
        req.getRequestDispatcher("/jsp/changeUserInfo.jsp").forward(req, resp);
    }

    private void updateUser(HttpServletRequest req, User user, String passwordToSave) {
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        User newUser = new User()
                .setUserId(user.getUserId())
                .setName(name)
                .setEmail(email)
                .setAdmin(user.isAdmin())
                .setPassword(passwordToSave);
        userService.update(newUser);
        req.getSession().setAttribute("user", newUser);
        req.getSession().setAttribute("Alert", "Your user info updated");
    }
}
