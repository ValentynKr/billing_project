package com.epam.billing.controller;

import com.epam.billing.entity.Activity;
import com.epam.billing.entity.ActivityCategory;
import com.epam.billing.entity.Language;
import com.epam.billing.service.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/deleteCategoryAdmin"})
public class DeleteCategoryAdminServlet extends HttpServlet {

    private LanguageService languageService;
    private ActivityService activityService;
    private UserActivityService userActivityService;
    private ActivityCategoryService activityCategoryService;



    @Override
    public void init() {
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());
        int activityCategoryId = Integer.parseInt(req.getParameter("categoryIdToDelete"));
        ActivityCategory activityCategory = activityCategoryService.getById(activityCategoryId);
        activityCategoryService.delete(activityCategory);
        req.getSession().setAttribute("userActivities",
                userActivityService.getAllUserActivitiesDurationDTO(language.getId()));
        req.getSession().setAttribute("listOfAllActivities", activityService.getAll());
        req.getSession().setAttribute("listOfAllActivityCategoriesWithStatus", activityCategoryService.getAllWithLocalizedNameStatusDTO(language.getId()));
        req.getSession().setAttribute("listOfAllActivitiesWithLocalizedCategories", activityService.getAllWithCategoryLocalizedNames(language.getId()));
        req.getSession().setAttribute("listOfOpenedActivityCategories", activityCategoryService.getOpenedWithLocalizedNames(language.getId()));
        resp.sendRedirect("/billing_project/jsp/editOrDeleteCategory.jsp");
    }
}