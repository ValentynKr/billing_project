package com.epam.billing.controller;

import com.epam.billing.entity.*;
import com.epam.billing.service.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/userRequestDeleteServlet"})
public class UserRequestDeleteServlet extends HttpServlet {
    private ActivityService activityService;
    private UserRequestService userRequestService;

    @Override
    public void init() {
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        userRequestService = (UserRequestService) getServletContext().getAttribute("userRequestService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commentForAdmin = req.getParameter("commentForAdmin");
        String activityNameForRequest = req.getParameter("activityNameForRequest");
        Activity activity = activityService.getByNameNotSafe(activityNameForRequest);
        UserRequest userRequest = new UserRequest();
        User user = (User) req.getSession().getAttribute("user");
        userRequest
                .setUserId(user.getUserId())
                .setRequestType(RequestType.DELETE)
                .setRequestStatus(RequestStatus.UNRESOLVED)
                .setActivityCategoryId(activity.getCategoryOfActivityId())
                .setActivityId(activity.getActivityId())
                .setUserActivityDuration(0)
                .setNewActivityName(null)
                .setComment(commentForAdmin);
        userRequestService.save(userRequest);
        req.getSession().setAttribute("Alert", "Your request was sent to administrator. Please, wait for approving");
        req.getRequestDispatcher("/jsp/requestFormForDelete.jsp").forward(req, resp);
    }
}
