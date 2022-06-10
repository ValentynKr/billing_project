package com.epam.billing.controller;

import com.epam.billing.entity.Activity;
import com.epam.billing.entity.ActivityCategory;
import com.epam.billing.utils.PasswordHashingUtil;
import com.epam.billing.utils.ValidationUtil;
import com.epam.billing.entity.User;
import com.epam.billing.service.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/editActivityAdmin"})
public class EditActivityAdminServlet extends HttpServlet {
    private UserService userService;
    private ActivityService activityService;
    private ActivityCategoryService activityCategoryService;

    @Override
    public void init() {
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        userService = (UserService) getServletContext().getAttribute("userService");
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int activityId = Integer.parseInt(req.getParameter("activityIdToEdit"));
        Activity activity = activityService.getById(activityId);
        int categoryId = activity.getCategoryOfActivityId();
        List<ActivityCategory> activityCategoryList = (List<ActivityCategory>) req.getSession().getAttribute("listOfAllActivityCategories");
        ActivityCategory category = new ActivityCategory();
        for (ActivityCategory activityCategory : activityCategoryList) {
            if (activityCategory.getCategoryId() == categoryId) {
                category = activityCategory;
            }
        }
        activityCategoryList.remove(category);
        activityCategoryList.add(0, category);
        req.getSession().setAttribute("listOfAllActivityCategories", activityCategoryList);
        req.getSession().setAttribute("activityToEdit", activity);
        req.getRequestDispatcher("/jsp/activityEditingForm.jsp").forward(req, resp);
    }
}