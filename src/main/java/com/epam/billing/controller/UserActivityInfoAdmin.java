package com.epam.billing.controller;

import com.epam.billing.dto.UserActivityUserNameIdDurationRecordingDTO;
import com.epam.billing.entity.Language;
import com.epam.billing.entity.User;
import com.epam.billing.service.ActivityService;
import com.epam.billing.service.LanguageService;
import com.epam.billing.service.UserActivityService;
import com.epam.billing.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(urlPatterns = {"/userActivityInfoAdmin"})
public class UserActivityInfoAdmin extends HttpServlet {
    private ActivityService activityService;
    private LanguageService languageService;
    private UserService userService;
    private UserActivityService userActivityService;

    @Override
    public void init() {
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
        userService = (UserService) getServletContext().getAttribute("userService");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userIdForUserActivities = Integer.parseInt(req.getParameter("userIdForUserActivities"));
        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());
        User user = userService.getById(userIdForUserActivities);
        List<UserActivityUserNameIdDurationRecordingDTO> userActivities = userActivityService.getUserActivityUserNameIdDurationDTO(userIdForUserActivities, language.getId());
        float totalTimeSpent = 0;
        for (UserActivityUserNameIdDurationRecordingDTO activity: userActivities) {
            totalTimeSpent += activity.getActivityDuration();
        }
        req.getSession().setAttribute("totalTimeSpent", totalTimeSpent);
        req.getSession().setAttribute("nameOfUserForUserActivities", user.getName());
        req.getSession().setAttribute("userActivities", userActivities);
        req.getRequestDispatcher("/jsp/watchUserActivities.jsp").forward(req, resp);
    }
}
