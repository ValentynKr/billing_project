package com.epam.billing.controller;

import com.epam.billing.entity.Language;
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
import java.util.Optional;

@WebServlet(urlPatterns = {"/changeUserInfo"})
public class ChangeUserInfoServlet extends HttpServlet {
    private static final String EMAIL_IS_INVALID_MESSAGE = "Chosen email is invalid. It should`n contain special symbols or cyrillic letters. Example - ggg@gmail.com";
    private static final String PASSWORD_IS_INVALID_MESSAGE = "Chosen pass is invalid. It should contain at least 8 symbols, including number, letter and special symbol";
    private UserService userService;
    private LanguageService languageService;
    private UserActivityService userActivityService;


    @Override
    public void init() {
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        String newPassword = req.getParameter("password");
        String currentPassword = user.getPassword();
        String newEmail = req.getParameter("email");
        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());


        if (user.getEmail().equals(newEmail)) {
            validateEmailPassAndUpdateUser(req, resp, language, user, newPassword, currentPassword, newEmail);
        } else {
            Optional<User> potentiallyExistingUser = userService.getByEmail(newEmail);
            if (potentiallyExistingUser.isPresent()) {
                req.getSession().setAttribute("Alert", "User with such email already exists");
                resp.sendRedirect("/billing_project/jsp/changeUserInfo.jsp");
            } else {
                validateEmailPassAndUpdateUser(req, resp, language, user, newPassword, currentPassword, newEmail);
            }
        }
    }

    private void validateEmailPassAndUpdateUser(HttpServletRequest req, HttpServletResponse resp, Language language, User user, String newPassword, String currentPassword, String newEmail) throws IOException {
        if (newPassword.isEmpty()) {
            if (ValidationUtil.isEmailValid(newEmail)) {
                updateUser(req, user, currentPassword, newEmail);
            } else {
                req.getSession().setAttribute("Alert", EMAIL_IS_INVALID_MESSAGE);
            }
        } else {
            if (ValidationUtil.isEmailValid(newEmail)) {
                if (ValidationUtil.isPasswordValid(newPassword)) {
                    updateUser(req, user, PasswordHashingUtil.getSaltedHash(newPassword), newEmail);
                } else {
                    req.getSession().setAttribute("Alert", PASSWORD_IS_INVALID_MESSAGE);
                }
            } else {
                req.getSession().setAttribute("Alert", EMAIL_IS_INVALID_MESSAGE);
            }
        }
        req.getSession().setAttribute("userActivities",
                userActivityService.getUserActivityUserNameIdDurationDTO(user.getUserId(), language.getId()));
        resp.sendRedirect("/billing_project/jsp/changeUserInfo.jsp");
    }

    private void updateUser(HttpServletRequest req, User user, String passwordToSave, String newEmail) {
        String name = req.getParameter("name");
        User newUser = new User()
                .setUserId(user.getUserId())
                .setName(name)
                .setEmail(newEmail)
                .setAdmin(user.isAdmin())
                .setPassword(passwordToSave);
        userService.update(newUser);
        req.getSession().setAttribute("user", newUser);
        req.getSession().setAttribute("Alert", "Your user info updated");
    }
}
