package com.epam.billing.controller;

import com.epam.billing.entity.*;
import com.epam.billing.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/editActivityAdminForm"})
public class EditActivityAdminFormServlet extends HttpServlet {
    private static final String ACTIVITY_CATEGORY_IS_CLOSED_MESSAGE = "You can not change activity from closed category. Please, make category OPENED and try again";
    private static final String ADDING_TO_CLOSED_CATEGORY_IS_FORBIDDEN_MESSAGE = "You can not change activity category to closed activity category. Please, change targeted category to OPENED and try again";
    private static final String STATUS_CLOSED = "CLOSED";
    private UserService userService;
    private UserActivityService userActivityService;
    private LanguageService languageService;
    private ActivityCategoryService activityCategoryService;
    private ActivityService activityService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");
        activityService = (ActivityService) getServletContext().getAttribute("activityService");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());

        ActivityCategory oldActivityCategory = (ActivityCategory) req.getSession().getAttribute("activityCategoryOfNotEditedActivity");
        int idOfOldCategory = oldActivityCategory.getCategoryId();

        String nameOfNewCategory = req.getParameter("activityCategoryOfEditedActivity");
        ActivityCategory newActivityCategory = activityCategoryService.getByNameNotSafe(nameOfNewCategory, language.getId());
        int idOfNewCategory = newActivityCategory.getCategoryId();

        Activity newActivity = (Activity) req.getSession().getAttribute("activityToEdit");

        if (idOfOldCategory != idOfNewCategory) {
            ActivityCategoryStatus activityCategoryStatusOfOld = oldActivityCategory.getActivityCategoryStatus();
            ActivityCategoryStatus activityCategoryStatusOfNew = newActivityCategory.getActivityCategoryStatus();
            if ((activityCategoryStatusOfOld.toString()).equals(STATUS_CLOSED)) {
                req.getSession().setAttribute("Alert", ACTIVITY_CATEGORY_IS_CLOSED_MESSAGE);
                req.getRequestDispatcher("/jsp/activityEditingForm.jsp").forward(req, resp);
                return;
            } else {
                if ((activityCategoryStatusOfNew.toString()).equals(STATUS_CLOSED)) {
                    req.getSession().setAttribute("Alert", ADDING_TO_CLOSED_CATEGORY_IS_FORBIDDEN_MESSAGE);
                    req.getRequestDispatcher("/jsp/activityEditingForm.jsp").forward(req, resp);
                    return;
                } else {
                    newActivity.setCategoryOfActivityId(idOfNewCategory);
                }
            }
        }
        newActivity.setName(req.getParameter("name"));
        activityService.update(newActivity);
        req.getSession().setAttribute("listOfAllActivityCategories", activityCategoryService.getAllWithLocalizedNames(language.getId()));
        req.getSession().setAttribute("userActivities",
                userActivityService.getAllUserActivitiesDurationDTO(language.getId()));
        req.getSession().setAttribute("listOfAllActivities", activityService.getAll());
        req.getSession().setAttribute("listOfAllActivitiesWithLocalizedCategories", activityService.getAllWithCategoryLocalizedNames(language.getId()));
        req.getRequestDispatcher("/jsp/editOrDeleteActivity.jsp").forward(req, resp);
    }
}
