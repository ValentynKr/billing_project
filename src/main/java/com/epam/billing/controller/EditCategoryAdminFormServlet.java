package com.epam.billing.controller;

import com.epam.billing.entity.ActivityCategory;
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

@WebServlet(urlPatterns = {"/editCategoryAdminForm"})
public class EditCategoryAdminFormServlet extends HttpServlet {

    private LanguageService languageService;
    private ActivityCategoryService activityCategoryService;
    private ActivityService activityService;
    private ActivityCategoryDescriptionService activityCategoryDescriptionService;

    @Override
    public void init() {
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        activityCategoryDescriptionService = (ActivityCategoryDescriptionService) getServletContext().getAttribute("activityCategoryDescriptionService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());

        String newActivityCategoryStatus = req.getParameter("ActivityCategoryStatus");
        String newActivityCategoryNameEn = req.getParameter("newActivityCategoryNameEn");
        String newActivityCategoryNameRu = req.getParameter("newActivityCategoryNameRu");
        String newCategoryActivityIdStr = req.getParameter("categoryId");
        int newCategoryActivityId = Integer.parseInt(newCategoryActivityIdStr);

        if (activityCategoryDescriptionService.getByNameExceptId(newCategoryActivityId, newActivityCategoryNameEn).isPresent()) {
            req.getSession().setAttribute("Alert", "Activity category with such english name already exists");
            req.getRequestDispatcher("/jsp/categoryEditingForm.jsp").forward(req, resp);
        } else {
            if (activityCategoryDescriptionService.getByNameExceptId(newCategoryActivityId, newActivityCategoryNameRu).isPresent()) {
                req.getSession().setAttribute("Alert", "Activity category with such russian name already exists");
                req.getRequestDispatcher("/jsp/categoryEditingForm.jsp").forward(req, resp);
            } else {
                ActivityCategory newActivityCategory = new ActivityCategory();

                newActivityCategory
                        .setCategoryId(newCategoryActivityId)
                        .setActivityCategoryStatus(ActivityCategoryStatus.valueOf(newActivityCategoryStatus));

                List<String> activityCategoryDescriptions = new ArrayList<>();
                activityCategoryDescriptions.add(newActivityCategoryNameEn);
                activityCategoryDescriptions.add(newActivityCategoryNameRu);

                ActivityCategoryFacade activityCategoryFacade = new ActivityCategoryFacade();
                activityCategoryFacade.updateActivityCategoryWithDescription(newActivityCategory, activityCategoryDescriptions, activityCategoryService, activityCategoryDescriptionService);

                req.getSession().setAttribute("listOfAllActivities", activityService.getAll());
                req.getSession().setAttribute("listOfAllActivityCategoriesWithStatus", activityCategoryService.getAllWithLocalizedNameStatusDTO(language.getId()));
                req.getSession().setAttribute("listOfAllActivitiesWithLocalizedCategories", activityService.getAllWithCategoryLocalizedNames(language.getId()));
                req.getSession().setAttribute("listOfOpenedActivityCategories", activityCategoryService.getOpenedWithLocalizedNames(language.getId()));

                req.getRequestDispatcher("/jsp/editOrDeleteCategory.jsp").forward(req, resp);
            }
        }
    }
}

