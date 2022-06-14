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
    private ActivityService activityService;
    private UserRequestService userRequestService;
    private UserActivityService userActivityService;

    @Override
    public void init() {
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        userRequestService = (UserRequestService) getServletContext().getAttribute("userRequestService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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
