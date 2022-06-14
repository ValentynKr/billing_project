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

@WebServlet(urlPatterns = {"/userRequestInfoServlet"})
public class UserRequestInfoServlet extends HttpServlet {
    private LanguageService languageService;
    private UserRequestService userRequestService;
    private UserActivityService userActivityService;
    private ActivityService activityService;
    private ActivityCategoryDescriptionService activityCategoryDescriptionService;
    private UserService userService;

    @Override
    public void init() {
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
        userRequestService = (UserRequestService) getServletContext().getAttribute("userRequestService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        activityCategoryDescriptionService = (ActivityCategoryDescriptionService) getServletContext().getAttribute("activityCategoryDescriptionService");
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());

        // get UserRequest data for head labels and changes while acceptation
        int userRequestId = Integer.parseInt(req.getParameter("userRequestId"));
        UserRequest userRequest = userRequestService.getById(userRequestId);
        req.getSession().setAttribute("userRequest", userRequest);

        // get User name, cause from UserRequest it`s impossible
        User user = userService.getById(userRequest.getUserId());
        req.getSession().setAttribute("userNameRequest", user);

        // set open/closed buttons for acceptation
        boolean isAcceptable = (userRequest.getRequestStatus().toString()).equals("UNRESOLVED");
        req.getSession().setAttribute("userRequestIsAcceptable", isAcceptable);

        // get UserActivity to EDIT or DELETE request
        UserActivity oldUserActivity = userActivityService.getByActivityIdAndUserId(userRequest.getActivityId(), userRequest.getUserId());
        UserActivityUserNameIdDurationRecordingDTO oldUserActivityDTO = userActivityService.getByIdUserActivityUserNameIdDurationDTO(oldUserActivity.getUserActivityId(), language.getId());
        boolean unpredictedDeletionFlag = userRequestService.areUnresolvedEditRequestsPresent(userRequest.getUserId(), userRequest.getActivityId());
        req.getSession().setAttribute("unpredictedDeletionFlag", unpredictedDeletionFlag);
        req.getSession().setAttribute("oldUserActivity", oldUserActivityDTO);

        // get ActivityCategory description for INVOLVE or CREATE request
        Activity activity = activityService.getById(userRequest.getActivityId());
        ActivityCategoryDescription activityCategoryDescription = activityCategoryDescriptionService.getByIdLocalized(activity.getCategoryOfActivityId(), language.getId());
        req.getSession().setAttribute("activityCategoryDescription", activityCategoryDescription);
        req.getSession().setAttribute("activityToInvolve", activity);

        // controller part
        if ((userRequest.getRequestType().toString()).equals("EDIT")) {
            req.getRequestDispatcher("/jsp/userRequestEditForm.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/jsp/userRequestForm.jsp").forward(req, resp);
        }
    }
}
