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

@WebServlet(urlPatterns = {"/editUserAdminForm"})
public class EditUserAdminFormServlet extends HttpServlet {
    private static final String EMAIL_IS_INVALID_MESSAGE = "Chosen email is invalid. It should`n contain special symbols or cyrillic letters. Example - ggg@gmail.com";
    private static final String PASSWORD_IS_INVALID_MESSAGE = "Chosen pass is invalid. It should contain at least 8 symbols, including number, letter and special symbol";
    private UserService userService;
    private UserActivityService userActivityService;
    private LanguageService languageService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int isAdminNum = Integer.parseInt(req.getParameter("isAdmin"));
        boolean isAdmin;
        isAdmin = isAdminNum > 0;
        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());
        User user = (User) req.getSession().getAttribute("userToEdit");
        String newPassword = req.getParameter("password");
        String currentPassword = user.getPassword();
        if (newPassword.isEmpty()) {
            if (ValidationUtil.isEmailValid(req.getParameter("email"))) {
                updateUser(req, user, currentPassword, isAdmin);
            } else {
                req.getSession().setAttribute("Alert", EMAIL_IS_INVALID_MESSAGE);
            }
        } else {
            if (ValidationUtil.isEmailValid(req.getParameter("email"))) {
                if (ValidationUtil.isPasswordValid(req.getParameter("password"))) {
                    updateUser(req, user, PasswordHashingUtil.getSaltedHash(currentPassword), isAdmin);
                } else {
                    req.getSession().setAttribute("Alert", PASSWORD_IS_INVALID_MESSAGE);
                }
            } else {
                req.getSession().setAttribute("Alert", EMAIL_IS_INVALID_MESSAGE);
            }
        }
        req.getSession().setAttribute("userActivities",
                userActivityService.getAllUserActivitiesDurationDTO(language.getId()));
        resp.sendRedirect("/billing_project/jsp/editOrDeleteUser.jsp");
    }

    private void updateUser(HttpServletRequest req, User user, String passwordToSave, boolean isAdmin) {
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        User newUser = new User()
                .setUserId(user.getUserId())
                .setName(name)
                .setEmail(email)
                .setAdmin(isAdmin)
                .setPassword(passwordToSave);
        userService.update(newUser);
        req.getSession().setAttribute("listOfAllUsers", userService.getAll());
    }
}
