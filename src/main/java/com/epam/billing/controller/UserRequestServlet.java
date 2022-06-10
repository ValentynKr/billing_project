package com.epam.billing.controller;

import com.epam.billing.entity.Activity;
import com.epam.billing.entity.RequestStatus;
import com.epam.billing.entity.RequestType;
import com.epam.billing.entity.UserRequest;
import com.epam.billing.service.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/userRequestServlet"})
public class UserRequestServlet extends HttpServlet {
    private ActivityService activityService;
    private UserRequestService userRequestService;

    @Override
    public void init() {
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        userRequestService = (UserRequestService) getServletContext().getAttribute("userRequestService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserRequest request = new UserRequest();
        String type = req.getParameter("typeOfRequest");
        String comment = req.getParameter("commentForAdmin");
        if (comment.isEmpty()) {
            comment = null;
        }
        String newActivityName = req.getParameter("newActivityName");
        if (newActivityName.isEmpty()) {
            newActivityName = null;
        }
        int userId = Integer.parseInt(req.getParameter("userId"));
        String activityName = req.getParameter("activityNameForRequest");
        Activity activity = activityService.getByNameNotSafe(activityName);
        request = request.setRequestStatus(RequestStatus.UNRESOLVED)
                .setRequestType(RequestType.valueOf(type)).setNewActivityName(newActivityName)
                .setUserId(userId).setActivityId(activity.getActivityId()).setComment(comment);
        userRequestService.save(request);
        req.getSession().setAttribute("Alert", "Your request was sent to user. Please wait for accepting");
        req.getRequestDispatcher("/jsp/createRequestFromUser.jsp").forward(req, resp);
    }
}
