package com.epam.billing.controller;

import com.epam.billing.entity.Activity;
import com.epam.billing.entity.Language;
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

@WebServlet(urlPatterns = {"/addUserActivityDuration"})
public class AddingUserActivityDurationServlet extends HttpServlet {
    private ActivityService activityService;
    private UserActivityService userActivityService;
    private LanguageService languageService;

    @Override
    public void init() {
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        String duration = req.getParameter("userActivityNewDuration");
        String userActivityName = req.getParameter("userActivityName");
        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());


        if (ValidationUtil.isActivityDurationValid(duration)) {

            duration = duration.replace(",", ".");
            float durationFloat = Float.parseFloat(duration);  // <--- try/catch clause need to be put in (local)
            Activity activity = activityService.getByNameNotSafe(userActivityName);
            UserActivity userActivity = userActivityService.getByActivityIdAndUserId(activity.getActivityId(), user.getUserId());
            userActivity.setDurationOfActivity(durationFloat + userActivity.getDurationOfActivity());
            userActivityService.update(userActivity);
            req.getSession().setAttribute("userActivities", userActivityService
                    .getUserActivityUserNameIdDurationDTO(user.getUserId(), language.getId()));
        } else {
            String alertMessage = String.format("Invalid value '%s' in field 'Duration'", duration);
            req.getSession().setAttribute("Alert", alertMessage);
        }
        resp.sendRedirect("/billing_project/jsp/welcome.jsp");
    }
}
