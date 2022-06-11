package com.epam.billing.controller;

import com.epam.billing.dto.ActivityCategoryIdLocalizedNameDTO;
import com.epam.billing.dto.ActivityCategoryIdLocalizedNameStatusDTO;
import com.epam.billing.entity.Activity;
import com.epam.billing.entity.ActivityCategory;
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
    private ActivityService activityService;

    @Override
    public void init() {
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int activityId = Integer.parseInt(req.getParameter("activityIdToEdit"));
        Activity activity = activityService.getById(activityId);
        int categoryId = activity.getCategoryOfActivityId();
        List<ActivityCategoryIdLocalizedNameStatusDTO> activityCategoryList = (List<ActivityCategoryIdLocalizedNameStatusDTO>) req.getSession()
                .getAttribute("listOfAllActivityCategoriesWithStatus"); //toDo should be changed to UI
        ActivityCategoryIdLocalizedNameStatusDTO category = new ActivityCategoryIdLocalizedNameStatusDTO();
        for (ActivityCategoryIdLocalizedNameStatusDTO activityCategory : activityCategoryList) {
            if (activityCategory.getCategoryId() == categoryId) {
                category = activityCategory;
            }
        }
        activityCategoryList.remove(category);
        activityCategoryList.add(0, category);
        req.getSession().setAttribute("activityCategoryOfNotEditedActivity", category);
        req.getSession().setAttribute("listOfAllActivityCategories", activityCategoryList);
        req.getSession().setAttribute("activityToEdit", activity);
        req.getRequestDispatcher("/jsp/activityEditingForm.jsp").forward(req, resp);
    }
}