package com.epam.billing.controller;

import com.epam.billing.entity.*;
import com.epam.billing.service.ActivityService;
import com.epam.billing.service.UserRequestService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/userRequestInvolveServlet"})
public class UserRequestInvolveServlet extends HttpServlet {
    private ActivityService activityService;
    private UserRequestService userRequestService;

    @Override
    public void init() {
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        userRequestService = (UserRequestService) getServletContext().getAttribute("userRequestService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String activityIdStr = req.getParameter("activityId");
        String commentForAdmin = req.getParameter("commentForAdmin");
        int activityId = Integer.parseInt(activityIdStr);
        User user = (User) req.getSession().getAttribute("user");
        Activity activity = activityService.getById(activityId);
        UserRequest userRequest = new UserRequest();
        userRequest
                .setUserId(user.getUserId())
                .setRequestType(RequestType.INVOLVE)
                .setRequestStatus(RequestStatus.UNRESOLVED)
                .setActivityCategoryId(activity.getCategoryOfActivityId())
                .setActivityId(activity.getActivityId())
                .setUserActivityDuration(0)
                .setNewActivityName(null)
                .setComment(commentForAdmin);
        userRequestService.save(userRequest);
        req.getSession().setAttribute("Alert", "Your request was sent to administrator. Please, wait for approving");
        req.getRequestDispatcher("/jsp/requestFormForInvolve.jsp").forward(req, resp);
    }
}
