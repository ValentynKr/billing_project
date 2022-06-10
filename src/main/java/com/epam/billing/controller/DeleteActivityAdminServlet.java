package com.epam.billing.controller;

import com.epam.billing.entity.Activity;
import com.epam.billing.entity.Language;
import com.epam.billing.service.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/deleteActivityAdmin"})
public class DeleteActivityAdminServlet extends HttpServlet {

    private LanguageService languageService;
    private ActivityService activityService;
    private UserActivityService userActivityService;


    @Override
    public void init() {
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());
        int activityId = Integer.parseInt(req.getParameter("activityIdToDelete"));
        Activity activity = activityService.getById(activityId);
        activityService.delete(activity);
        req.getSession().setAttribute("userActivities",
                userActivityService.getAllUserActivitiesDurationDTO(language.getId()));
        req.getSession().setAttribute("listOfAllActivities", activityService.getAll());
        req.getSession().setAttribute("listOfAllActivitiesWithLocalizedCategories", activityService.getAllWithCategoryLocalizedNames(language.getId()));
        resp.sendRedirect("/billing_project/jsp/editOrDeleteActivity.jsp");
    }
}