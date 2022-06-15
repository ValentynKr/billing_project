package com.epam.billing.controller;

import com.epam.billing.dto.ActivityCategoryLocActivityUserActivityCountDTO;
import com.epam.billing.entity.*;
import com.epam.billing.service.ActivityService;
import com.epam.billing.service.LanguageService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet(urlPatterns = {"/watchAllActivitiesServlet"})
public class WatchAllActivitiesServlet extends HttpServlet {
    private ActivityService activityService;
    private LanguageService languageService;

    @Override
    public void init() {
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sortCriteria = req.getParameter("sort");
        String activityCategoryIdToFilter = req.getParameter("filter");

        if (activityCategoryIdToFilter != null) {
            int activityCategoryId = Integer.parseInt(activityCategoryIdToFilter);
            List<ActivityCategoryLocActivityUserActivityCountDTO> listOfActivitiesFiltered = new ArrayList<>();
            List<ActivityCategoryLocActivityUserActivityCountDTO> listOfActivities = (List<ActivityCategoryLocActivityUserActivityCountDTO>) req.getSession().getAttribute("allActivityCount");
            for (ActivityCategoryLocActivityUserActivityCountDTO userActivity : listOfActivities) {
                if (userActivity.getActivityCategoryId() == activityCategoryId) {
                    listOfActivitiesFiltered.add(userActivity);
                }
            }
            req.getSession().setAttribute("chosenCategoryToFilter", listOfActivitiesFiltered.get(0).getActivityCategoryName());
            req.getSession().setAttribute("allActivityCountFiltered", listOfActivitiesFiltered);
            req.getRequestDispatcher("/jsp/watchAllActivitiesAdminFiltered.jsp").forward(req, resp);
        }

        if (sortCriteria != null) {
            int criteria = Integer.parseInt(sortCriteria);
            List<ActivityCategoryLocActivityUserActivityCountDTO> listOfActivities = (List<ActivityCategoryLocActivityUserActivityCountDTO>) req.getSession().getAttribute("allActivityCount");
            if (criteria == 1) {
                sortByCategoryName(listOfActivities);
            }
            if (criteria == 2) {
                sortByActivityName(listOfActivities);
            }
            if (criteria == 3) {
                sortByUserActivitiesNumber(listOfActivities);
            }
            req.getSession().setAttribute("allActivityCount", listOfActivities);
            req.getRequestDispatcher("/jsp/watchAllActivitiesAdmin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());
        List<ActivityCategoryLocActivityUserActivityCountDTO> listOfActivities = activityService.getActivityCategoryLocActivityUserActivityCountDTO(language.getId());
        req.getSession().setAttribute("allActivityCount", listOfActivities);
        req.getRequestDispatcher("/jsp/watchAllActivitiesAdmin.jsp").forward(req, resp);
    }

    private void sortByUserActivitiesNumber(List<ActivityCategoryLocActivityUserActivityCountDTO> listOfActivities) {
        listOfActivities.sort((one, two) -> {
            if (one.getNumberOfUserActivities() == two.getNumberOfUserActivities()) {
                return 0;
            } else {
                return one.getNumberOfUserActivities() < two.getNumberOfUserActivities() ? 1 : -1;
            }
        });
    }

    private void sortByActivityName(List<ActivityCategoryLocActivityUserActivityCountDTO> listOfActivities) {
        listOfActivities.sort((one, two) -> {
            if (one.getActivityName().equals(two.getActivityName())) {
                return 0;
            } else {
                return one.getActivityName().compareTo(two.getActivityName());
            }
        });
    }

    private void sortByCategoryName(List<ActivityCategoryLocActivityUserActivityCountDTO> listOfActivities) {
        listOfActivities.sort((one, two) -> {
            if (one.getActivityCategoryName().equals(two.getActivityCategoryName())) {
                return 0;
            } else {
                return one.getActivityCategoryName().compareTo(two.getActivityCategoryName());
            }
        });
    }
}
