package com.epam.billing.controller;

import com.epam.billing.dto.ActivityCategoryIdLocalizedNameStatusDTO;
import com.epam.billing.entity.*;
import com.epam.billing.service.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/userRequestCreateServlet"})
public class UserRequestCreateServlet extends HttpServlet {
    private ActivityService activityService;
    private UserRequestService userRequestService;
    private ActivityCategoryService activityCategoryService;
    private LanguageService languageService;



    @Override
    public void init() {
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        userRequestService = (UserRequestService) getServletContext().getAttribute("userRequestService");
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());

        String activityCategoryName = req.getParameter("activityCategoryName");
        String newActivityName = req.getParameter("newActivityName");
        String commentForAdmin = req.getParameter("commentForAdmin");
        User user = (User) req.getSession().getAttribute("user");
        ActivityCategoryIdLocalizedNameStatusDTO activityCategoryIdLocalizedNameStatusDTO = activityCategoryService.getByNameNotSafe(activityCategoryName, language.getId());
        int activityCategoryId = activityCategoryIdLocalizedNameStatusDTO.getCategoryId();
        UserRequest userRequest = new UserRequest();
        if (commentForAdmin.isEmpty()) {
            commentForAdmin = null;
        }
        userRequest
                .setUserId(user.getUserId())
                .setRequestType(RequestType.CREATE)
                .setRequestStatus(RequestStatus.UNRESOLVED)
                .setActivityCategoryId(activityCategoryId)
                .setActivityId(1)
                .setUserActivityDuration(0)
                .setNewActivityName(newActivityName)
                .setComment(commentForAdmin);
        userRequestService.save(userRequest);
        req.getSession().setAttribute("Alert", "Your request was sent to administrator. Please, wait for approving");
        req.getRequestDispatcher("/jsp/requestFormForCreate.jsp").forward(req, resp);
    }
}
