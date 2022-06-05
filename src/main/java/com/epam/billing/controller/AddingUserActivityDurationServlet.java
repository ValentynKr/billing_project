package com.epam.billing.controller;

import com.epam.billing.DTO.UserActivityUserNameIdDurationRecording;
import com.epam.billing.entity.Activity;
import com.epam.billing.entity.User;
import com.epam.billing.entity.UserActivity;
import com.epam.billing.service.*;
import com.epam.billing.utils.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/addUserActivityDuration"})
public class AddingUserActivityDurationServlet extends HttpServlet {
    private UserService userService;
    private ActivityService activityService;
    private ActivityCategoryService activityCategoryService;
    private UserActivityService userActivityService;
    private UserRequestService userRequestService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
        userRequestService = (UserRequestService) getServletContext().getAttribute("userRequestService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        String duration = req.getParameter("userActivityNewDuration");
        String userActivityName = req.getParameter("userActivityName");

        if (ValidationUtil.isActivityDurationValid(duration)) {
            duration = duration.replace(",", ".");
            float durationFloat = Float.parseFloat(duration);
            Activity activity = activityService.getByName(userActivityName);
            UserActivity userActivity = userActivityService.getByActivityIdAndUserId(activity.getActivityId(), user.getUserId());
            userActivity.setDurationOfActivity(durationFloat + userActivity.getDurationOfActivity());
            userActivityService.update(userActivity);
            req.getSession().setAttribute("userActivities", userActivityService.getUserActivityUserNameIdDurationDTO(user.getUserId()));
        } else {
            String alertMessage = String.format("Invalid value '%s' in field 'Duration'", duration);
            req.getSession().setAttribute("Alert", alertMessage);
        }
        resp.sendRedirect("/billing_project/jsp/welcome.jsp");
    }
}
