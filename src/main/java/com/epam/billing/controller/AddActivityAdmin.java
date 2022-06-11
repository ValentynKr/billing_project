package com.epam.billing.controller;


import com.epam.billing.dto.ActivityCategoryIdLocalizedNameStatusDTO;
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

@WebServlet(urlPatterns = {"/addActivityAdmin"})
public class AddActivityAdmin extends HttpServlet {
    private ActivityService activityService;
    private ActivityCategoryService activityCategoryService;
    private LanguageService languageService;

    @Override
    public void init() {
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());
        String newActivityName = req.getParameter("newActivityName");
        String activityCategoryOfNewActivity = req.getParameter("activityCategoryOfNewActivity");


        ActivityCategoryIdLocalizedNameStatusDTO activityCategory = activityCategoryService
                .getByNameNotSafe(activityCategoryOfNewActivity, language.getId());

        if (activityService.getByNameInOneCategory(newActivityName, activityCategory.getCategoryId()).isPresent()) {
            req.getSession().setAttribute("Alert", "Activity with such name already exists in chosen category");
        } else {
            Activity newActivity = new Activity()
                    .setCategoryOfActivityId(activityCategory.getCategoryId())
                    .setName(newActivityName);
            activityService.save(newActivity);
            req.getSession().setAttribute("Alert", "Activity added");
            req.getSession().setAttribute("listOfAllActivities", activityService.getAll());
            req.getSession().setAttribute("listOfAllActivitiesWithLocalizedCategories", activityService.getAllWithCategoryLocalizedNames(language.getId()));
        }
        req.getRequestDispatcher("/jsp/addActivityAdmin.jsp").forward(req, resp);

    }
}