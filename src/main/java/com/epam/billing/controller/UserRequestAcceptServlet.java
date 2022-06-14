package com.epam.billing.controller;

import com.epam.billing.dto.UserActivityUserNameIdDurationRecordingDTO;
import com.epam.billing.entity.*;
import com.epam.billing.service.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/userRequestAcceptServlet"})
public class UserRequestAcceptServlet extends HttpServlet {
    private LanguageService languageService;
    private UserRequestService userRequestService;
    private UserActivityService userActivityService;
    private ActivityService activityService;
    private UserService userService;
    private ActivityCategoryService activityCategoryService;



    @Override
    public void init() {
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
        userRequestService = (UserRequestService) getServletContext().getAttribute("userRequestService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        userService = (UserService) getServletContext().getAttribute("userService");
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());
        UserRequest userRequest = ((UserRequest) req.getSession().getAttribute("userRequest"));

        if ((userRequest.getRequestType().toString()).equals("EDIT")) {
            editActivityOrUserActivity(req);
        }
        if ((userRequest.getRequestType().toString()).equals("DELETE")) {
            deleteUserActivity(req);
        }
        if ((userRequest.getRequestType().toString()).equals("INVOLVE")) {
            createUserActivity(userRequest);
        }
        if ((userRequest.getRequestType().toString()).equals("CREATE")) {
            createActivityAndUserActivity(userRequest);
        }
        userRequest.setRequestStatus(RequestStatus.RESOLVED);
        userRequestService.update(userRequest);
        req.getSession().setAttribute("userActivities",
                userActivityService.getAllUserActivitiesDurationDTO(language.getId()));
        req.getSession().setAttribute("listOfAllUsers", userService.getAll());
        req.getSession().setAttribute("listOfOpenedActivityCategories", activityCategoryService.getOpenedWithLocalizedNames(language.getId()));
        req.getSession().setAttribute("listOfAllActivityCategories", activityCategoryService.getAllWithLocalizedNames(language.getId()));
        req.getSession().setAttribute("listOfAllActivityCategoriesWithStatus", activityCategoryService.getAllWithLocalizedNameStatusDTO(language.getId()));
        req.getSession().setAttribute("listOfAllActivities", activityService.getAll());
        req.getSession().setAttribute("listOfAllActivitiesWithLocalizedCategories", activityService.getAllWithCategoryLocalizedNames(language.getId()));
        req.getSession().setAttribute("listOfUserRequests", userRequestService.getAllWithUserNames());
        resp.sendRedirect("/billing_project/jsp/userRequestsAdmin.jsp");
    }

    private void deleteUserActivity(HttpServletRequest req) {
        UserActivityUserNameIdDurationRecordingDTO oldUserActivityDTO  = (UserActivityUserNameIdDurationRecordingDTO) req.getSession().getAttribute("oldUserActivity");
        UserActivity oldUserActivity = userActivityService.getById(oldUserActivityDTO.getUserActivityId());
        userActivityService.delete(oldUserActivity);
    }

    private void createUserActivity(UserRequest userRequest) {
        UserActivity newUserActivity = new UserActivity();
        newUserActivity
                .setUserActivityId(1)
                .setActivityId(userRequest.getActivityId())
                .setDurationOfActivity(0)
                .setUserId(userRequest.getUserId());
        userActivityService.save(newUserActivity);
    }

    private void createActivityAndUserActivity(UserRequest userRequest) {
        Activity newActivity = new Activity();
        newActivity
                .setCategoryOfActivityId(userRequest.getActivityCategoryId())
                .setActivityId(userRequest.getActivityId())
                .setName(userRequest.getNewActivityName());
        activityService.save(newActivity);
        newActivity = activityService.getByNameNotSafe(newActivity.getName());
        UserActivity newUserActivity = new UserActivity();
        newUserActivity
                .setActivityId(newActivity.getActivityId())
                .setDurationOfActivity(0)
                .setUserId(userRequest.getUserId());
        userActivityService.save(newUserActivity);
    }

    private void editActivityOrUserActivity(HttpServletRequest req) {
        UserActivityUserNameIdDurationRecordingDTO oldUserActivityDTO  = (UserActivityUserNameIdDurationRecordingDTO) req.getSession().getAttribute("oldUserActivity");
        UserActivity oldUserActivity = userActivityService.getById(oldUserActivityDTO.getUserActivityId());
        String newActivityName = req.getParameter("newActivityName");
        float newUserActivityDuration = Float.parseFloat(req.getParameter("newUserActivityDuration"));

        if (!newActivityName.isEmpty()) {
            Activity newActivity = activityService.getById(oldUserActivity.getActivityId());
            newActivity.setName(newActivityName);
            activityService.update(newActivity);
        }
        oldUserActivity.setDurationOfActivity(newUserActivityDuration);
        userActivityService.update(oldUserActivity);
    }
}
