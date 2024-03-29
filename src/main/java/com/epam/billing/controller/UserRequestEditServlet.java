package com.epam.billing.controller;

import com.epam.billing.entity.*;
import com.epam.billing.service.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/userRequestEditServlet"})
public class UserRequestEditServlet extends HttpServlet {
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

        String userActivityName = req.getParameter("userActivityName");
        String userActivityNewDuration = req.getParameter("userActivityNewDuration");
        String commentForAdmin = req.getParameter("commentForAdmin");
        User user = (User) req.getSession().getAttribute("user");
        Activity activity = activityService.getByNameNotSafe(userActivityName);
        UserActivity userActivity = userActivityService.getByActivityIdAndUserId(activity.getActivityId(), user.getUserId());

        userActivity.setDurationOfActivity(Float.parseFloat(userActivityNewDuration));
        UserRequest userRequest = new UserRequest();
        userRequest
                .setUserId(user.getUserId())
                .setRequestType(RequestType.EDIT)
                .setRequestStatus(RequestStatus.UNRESOLVED)
                .setActivityCategoryId(activity.getCategoryOfActivityId())
                .setActivityId(activity.getActivityId())
                .setUserActivityDuration(userActivity.getDurationOfActivity())
                .setNewActivityName(null)
                .setComment(commentForAdmin);
        userRequestService.save(userRequest);
        req.getSession().setAttribute("Alert", "Your request was sent to administrator. Please, wait for approving");
        req.getRequestDispatcher("/jsp/requestFormForEdit.jsp").forward(req, resp);
    }
}
