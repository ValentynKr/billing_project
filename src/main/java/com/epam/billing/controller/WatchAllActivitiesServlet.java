package com.epam.billing.controller;

import com.epam.billing.dto.ActivityCategoryLocActivityUserActivityCountDTO;
import com.epam.billing.entity.*;
import com.epam.billing.service.ActivityCategoryService;
import com.epam.billing.service.ActivityService;
import com.epam.billing.service.LanguageService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@WebServlet(urlPatterns = {"/watchAllActivitiesServlet"})
public class WatchAllActivitiesServlet extends HttpServlet {
    private ActivityService activityService;
    private LanguageService languageService;
    private ActivityCategoryService activityCategoryService;
    private final Map<Integer, String> sortingCriteria = new HashMap<>();

    @Override
    public void init() {
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");
        sortingCriteria.put(1, "activity_category_description.name");
        sortingCriteria.put(2, "a.name");
        sortingCriteria.put(3, "counter desc");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());

        String sortCriteria = req.getParameter("sort");
        String activityCategoryIdToFilter = req.getParameter("filter");

        if (activityCategoryIdToFilter != null) {
            int activityCategoryId = Integer.parseInt(activityCategoryIdToFilter);
            List<ActivityCategoryLocActivityUserActivityCountDTO> listOfActivitiesFiltered = new ArrayList<>();
            List<ActivityCategoryLocActivityUserActivityCountDTO> listOfActivities = (List<ActivityCategoryLocActivityUserActivityCountDTO>) req.getSession().getAttribute("allActivityCount");
            for (ActivityCategoryLocActivityUserActivityCountDTO userActivity : listOfActivities) {
                if ((userActivity.getActivityCategoryId() == activityCategoryId)) {
                    listOfActivitiesFiltered.add(userActivity);
                }
            }
            if (listOfActivitiesFiltered.isEmpty()) {
                req.getSession().setAttribute("filteredActivitiesAreExistingFlag", false);
            } else {
                req.getSession().setAttribute("filteredActivitiesAreExistingFlag", true);
            }
            req.getSession().setAttribute("chosenCategoryToFilter", activityCategoryService.getByIdLocalized(activityCategoryId, language.getId()).getCategoryName());
            req.getSession().setAttribute("allActivityCountFiltered", listOfActivitiesFiltered);
            req.getRequestDispatcher("/jsp/watchAllActivitiesAdminFiltered.jsp").forward(req, resp);
        }

        if (sortCriteria != null) {
            req.getSession().setAttribute("allActivityCount", activityService.getActivityCategoryLocActivityUserActivityCountDTO(language.getId(),
                    sortingCriteria.get(Integer.parseInt(sortCriteria))));
            req.getRequestDispatcher("/jsp/watchAllActivitiesAdmin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());
        List<ActivityCategoryLocActivityUserActivityCountDTO> listOfActivities = activityService.getActivityCategoryLocActivityUserActivityCountDTO(language.getId(), "id");

        req.getSession().setAttribute("allActivityCount", listOfActivities);
        req.getRequestDispatcher("/jsp/watchAllActivitiesAdmin.jsp").forward(req, resp);
    }
}
