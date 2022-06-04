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
            String[] strings = duration.split(",");
            StringBuilder stringBuilder = new StringBuilder();
            if (strings.length > 0) {
                for (int i = 0; i < strings.length; i++) {
                    stringBuilder.append(strings[i]);
                    if (i != strings.length - 1) {
                        stringBuilder.append('.');
                    }
                }
                duration = stringBuilder.toString();
            }
            float durationFloat = Float.parseFloat(duration);
            Activity activity = activityService.getByName(userActivityName);
            List<UserActivity> userActivities = userActivityService.getByActivityId(activity.getActivityId());
            UserActivity newUserActivity = new UserActivity();
            for (UserActivity userActivity : userActivities) {
                if (userActivity.getUserId() == user.getUserId()) {
                    newUserActivity = userActivity;
                }
            }
            durationFloat += newUserActivity.getDurationOfActivity();
            newUserActivity.setDurationOfActivity(durationFloat);
            userActivityService.update(newUserActivity);

            req.getSession().removeAttribute("userActivities");
            UserActivityUserNameIdDurationRecording userActivityUserNameIdDurationRecording = new UserActivityUserNameIdDurationRecording();
            req.getSession().setAttribute("userActivities", userActivityUserNameIdDurationRecording
                    .getUserActivityUserNameIdDurationDTO(user.getUserId()));

        } else {
            req.getSession().removeAttribute("Alert");
            req.getSession().setAttribute("Alert", "Invalid value in field 'Duration'");
        }
        req.getRequestDispatcher("/jsp/welcome.jsp").forward(req, resp);
    }
}
