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

        int userRequestId = Integer.parseInt(req.getParameter("userRequestId"));
        UserActivity oldUserActivity = (UserActivity) req.getSession().getAttribute("oldUserActivityWithId");
        String newActivityName = req.getParameter("newActivityName");
        float newUserActivityDuration = Float.parseFloat(req.getParameter("newUserActivityDuration"));

        if (!newActivityName.isEmpty()) {
            Activity newActivity = activityService.getById(oldUserActivity.getActivityId());
            newActivity.setName(newActivityName);
            activityService.update(newActivity);
        }
        oldUserActivity.setDurationOfActivity(newUserActivityDuration);
        userActivityService.update(oldUserActivity);
        UserRequest request = userRequestService.getById(userRequestId);
        request.setRequestStatus(RequestStatus.RESOLVED);
        userRequestService.update(request);
        req.getSession().setAttribute("listOfUserRequests", userRequestService.getAllWithUserNames());
        resp.sendRedirect("/billing_project/jsp/userRequestsAdmin.jsp");
    }
}
