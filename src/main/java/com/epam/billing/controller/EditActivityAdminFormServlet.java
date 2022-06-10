package com.epam.billing.controller;

import com.epam.billing.entity.ActivityCategory;
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

@WebServlet(urlPatterns = {"/editActivityAdminForm"})
public class EditActivityAdminFormServlet extends HttpServlet {
    private UserService userService;
    private UserActivityService userActivityService;
    private LanguageService languageService;
    private ActivityCategoryService activityCategoryService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());


        req.getSession().setAttribute("listOfAllActivityCategories", activityCategoryService.getAllWithLocalizedNames(language.getId()));

    }
}
