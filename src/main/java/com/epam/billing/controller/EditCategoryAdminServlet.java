package com.epam.billing.controller;

import com.epam.billing.dto.ActivityCategoryIdLocalizedNameStatusDTO;
import com.epam.billing.service.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/editCategoryAdmin"})
public class EditCategoryAdminServlet extends HttpServlet {

    private ActivityCategoryService activityCategoryService;

    @Override
    public void init() {
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int activityCategoryId = Integer.parseInt(req.getParameter("categoryIdToEdit"));
        List<ActivityCategoryIdLocalizedNameStatusDTO> activityCategoryIdLocalizedNameStatusDTOList = activityCategoryService.getByIdWithDescriptions(activityCategoryId);
        req.getSession().setAttribute("activityCategoryToEditEn", activityCategoryIdLocalizedNameStatusDTOList.get(0));
        req.getSession().setAttribute("activityCategoryToEditRu", activityCategoryIdLocalizedNameStatusDTOList.get(1));
        req.getRequestDispatcher("/jsp/categoryEditingForm.jsp").forward(req, resp);
    }
}