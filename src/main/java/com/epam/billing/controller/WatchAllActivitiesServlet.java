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
    private final Map<Integer, Comparator<ActivityCategoryLocActivityUserActivityCountDTO>> criteriaToComparator = new HashMap<>();


    @Override
    public void init() {
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");
        criteriaToComparator.put(1, sortByCategoryName);
        criteriaToComparator.put(2, sortByActivityName);
        criteriaToComparator.put(3, sortByUserActivitiesNumber);

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
            int criteria = Integer.parseInt(sortCriteria);
            List<ActivityCategoryLocActivityUserActivityCountDTO> listOfActivities = (List<ActivityCategoryLocActivityUserActivityCountDTO>) req.getSession().getAttribute("allActivityCount");
            listOfActivities.sort(getComparatorBySortingCriteria(criteria));
            req.getSession().setAttribute("allActivityCount", listOfActivities);
            req.getRequestDispatcher("/jsp/watchAllActivitiesAdmin.jsp").forward(req, resp);
        }
    }

    private Comparator<? super ActivityCategoryLocActivityUserActivityCountDTO> getComparatorBySortingCriteria(int criteria) {
        return criteriaToComparator.get(criteria);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());
        List<ActivityCategoryLocActivityUserActivityCountDTO> listOfActivities = activityService.getActivityCategoryLocActivityUserActivityCountDTO(language.getId());

        req.getSession().setAttribute("allActivityCount", listOfActivities);
        req.getRequestDispatcher("/jsp/watchAllActivitiesAdmin.jsp").forward(req, resp);
    }

    private final Comparator<ActivityCategoryLocActivityUserActivityCountDTO> sortByUserActivitiesNumber = (one, two) -> {
        if (one.getNumberOfUserActivities() == two.getNumberOfUserActivities()) {
            return 0;
        } else {
            return one.getNumberOfUserActivities() < two.getNumberOfUserActivities() ? 1 : -1;
        }
    };

    private final Comparator<ActivityCategoryLocActivityUserActivityCountDTO> sortByCategoryName = (one, two) -> {
        if (one.getActivityCategoryName().equals(two.getActivityCategoryName())) {
            return 0;
        } else {
            return one.getActivityCategoryName().compareTo(two.getActivityCategoryName());
        }
    };

    private final Comparator<ActivityCategoryLocActivityUserActivityCountDTO> sortByActivityName = (one, two) -> {
        if (one.getActivityName().equals(two.getActivityName())) {
            return 0;
        } else {
            return one.getActivityName().compareTo(two.getActivityName());
        }
    };
}
