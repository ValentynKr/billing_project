package com.epam.billing.controller;

import com.epam.billing.dto.ActivityIdActivityCategoryLocalizedNameActivityNameDTO;
import com.epam.billing.entity.Language;
import com.epam.billing.entity.User;
import com.epam.billing.entity.UserActivity;
import com.epam.billing.service.ActivityCategoryService;
import com.epam.billing.service.ActivityService;
import com.epam.billing.service.LanguageService;
import com.epam.billing.service.UserActivityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/userRequestHandlingServlet"})
public class UserRequestHandlingServlet extends HttpServlet {
    private ActivityService activityService;
    private LanguageService languageService;
    private ActivityCategoryService activityCategoryService;
    private Map<String, String> typeOfRequestToJspPath;
    private UserActivityService userActivityService;


    @Override
    public void init() {
        initRequestTypePathMap();
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
    }

    private void initRequestTypePathMap() {
        typeOfRequestToJspPath = new HashMap<>();
        typeOfRequestToJspPath.put("CREATE", "/jsp/requestFormForCreate.jsp");
        typeOfRequestToJspPath.put("INVOLVE", "/jsp/requestFormForInvolve.jsp");
        typeOfRequestToJspPath.put("EDIT", "/jsp/requestFormForEdit.jsp");
        typeOfRequestToJspPath.put("DELETE", "/jsp/requestFormForDelete.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());
        String typeOfRequest = req.getParameter("typeOfRequest");
        String path = typeOfRequestToJspPath.get(typeOfRequest);
        if (typeOfRequest.equals("INVOLVE")) {
            List<ActivityIdActivityCategoryLocalizedNameActivityNameDTO> allActivities = activityService.getAllWithCategoryLocalizedNames(language.getId());
            User user = (User) req.getSession().getAttribute("user");
            List<UserActivity> allUserActivities = userActivityService.getByUserId(user.getUserId());
            List<Integer> activityIdsOfUserActivities = new ArrayList<>();
            for (UserActivity userActivity : allUserActivities) {
                activityIdsOfUserActivities.add(userActivity.getActivityId());
            }
            for (Integer activityIdsOfUserActivity : activityIdsOfUserActivities) {
                allActivities.removeIf(activity -> activity.getActivityId() == activityIdsOfUserActivity);
            }
            req.getSession().setAttribute("listOfAllLocalizedActivitiesButUsers", allActivities);
        }
        if (typeOfRequest.equals("CREATE")) {
            req.getSession().setAttribute("listOfAllActivityCategories", activityCategoryService.getAllWithLocalizedNames(language.getId()));
        }
        req.getRequestDispatcher(path).forward(req, resp);
    }
}
