package com.epam.billing.controller;

import com.epam.billing.entity.ActivityCategory;
import com.epam.billing.entity.ActivityCategoryDescription;
import com.epam.billing.entity.ActivityCategoryStatus;
import com.epam.billing.entity.Language;
import com.epam.billing.facade.ActivityCategoryFacade;
import com.epam.billing.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/addCategoryAdmin"})
public class AddCategoryAdmin extends HttpServlet {
    private ActivityService activityService;
    private ActivityCategoryService activityCategoryService;
    private LanguageService languageService;
    private ActivityCategoryDescriptionService activityCategoryDescriptionService;

    @Override
    public void init() {
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
        activityCategoryDescriptionService = (ActivityCategoryDescriptionService) getServletContext().getAttribute("activityCategoryDescriptionService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());
        String newCategoryNameEn = req.getParameter("newActivityCategoryNameEn");
        String newCategoryNameRu = req.getParameter("newActivityCategoryNameRu");
        String activityCategoryStatus = req.getParameter("ActivityCategoryStatus");

        if (activityCategoryService.getByNameSafe(newCategoryNameEn, 1).isPresent()) {
            req.getSession().setAttribute("Alert", "Activity category with such english name already exists");
        } else {
            if (activityCategoryService.getByNameSafe(newCategoryNameRu, 2).isPresent()) {
                req.getSession().setAttribute("Alert", "Activity category with such russian name already exists");
            } else {
                ActivityCategory newActivityCategory = new ActivityCategory();
                newActivityCategory.setActivityCategoryStatus(ActivityCategoryStatus.valueOf(activityCategoryStatus));
                List<String> activityCategoryDescriptions = new ArrayList<>();
                activityCategoryDescriptions.add(newCategoryNameEn);
                activityCategoryDescriptions.add(newCategoryNameRu);
                ActivityCategoryFacade activityCategoryFacade = new ActivityCategoryFacade();
                activityCategoryFacade.addActivityCategoryWithDescription(newActivityCategory, activityCategoryDescriptions, activityCategoryService, activityCategoryDescriptionService);
                req.getSession().setAttribute("Alert", "Activity category added");
                req.getSession().setAttribute("listOfAllActivities", activityService.getAll());
                req.getSession().setAttribute("listOfAllActivityCategoriesWithStatus", activityCategoryService.getAllWithLocalizedNameStatusDTO(language.getId()));
                req.getSession().setAttribute("listOfAllActivitiesWithLocalizedCategories", activityService.getAllWithCategoryLocalizedNames(language.getId()));
                req.getSession().setAttribute("listOfOpenedActivityCategories", activityCategoryService.getOpenedWithLocalizedNames(language.getId()));

            }
        }
        req.getRequestDispatcher("/jsp/addCategoryAdmin.jsp").forward(req, resp);
    }
}