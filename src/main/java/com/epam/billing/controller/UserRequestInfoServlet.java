package com.epam.billing.controller;

import com.epam.billing.dto.UserActivityUserNameIdDurationRecordingDTO;
import com.epam.billing.entity.Language;
import com.epam.billing.entity.UserActivity;
import com.epam.billing.entity.UserRequest;
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



    @Override
    public void init() {
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
        userRequestService = (UserRequestService) getServletContext().getAttribute("userRequestService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());

        int userRequestId = Integer.parseInt(req.getParameter("userRequestId"));
        UserRequest userRequest = userRequestService.getById(userRequestId);
        boolean isAcceptable = !(userRequest.getRequestStatus().toString()).equals("RESOLVED");
        req.getSession().setAttribute("userRequestIsAcceptable", isAcceptable); //toDo for closed or opened requests
        req.getSession().setAttribute("userRequest", userRequest);
        if ((userRequest.getRequestType().toString()).equals("EDIT")) {
            UserActivity oldUserActivity = userActivityService.getByActivityIdAndUserId(userRequest.getActivityId(), userRequest.getUserId());
            UserActivityUserNameIdDurationRecordingDTO oldUserActivityDTO = userActivityService.getByIdUserActivityUserNameIdDurationDTO(oldUserActivity.getUserActivityId(), language.getId());
            req.getSession().setAttribute("oldUserActivity", oldUserActivityDTO);
            req.getSession().setAttribute("oldUserActivityWithId", oldUserActivity);
            req.getRequestDispatcher("/jsp/userRequestEditForm.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/jsp/userRequestForm.jsp").forward(req, resp);
        }
    }
}
