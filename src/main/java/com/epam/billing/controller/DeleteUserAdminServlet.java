package com.epam.billing.controller;

import com.epam.billing.entity.Language;
import com.epam.billing.entity.User;
import com.epam.billing.service.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/deleteUserAdmin"})
public class DeleteUserAdminServlet extends HttpServlet {
    private UserService userService;
    private UserActivityService userActivityService;
    private LanguageService languageService;


    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());
        int userId = Integer.parseInt(req.getParameter("userIdToDelete"));
        User user = userService.getById(userId);
        userService.delete(user);
        req.getSession().setAttribute("userActivities",
                userActivityService.getAllUserActivitiesDurationDTO(language.getId()));
        req.getSession().setAttribute("listOfAllUsers", userService.getAll());
        resp.sendRedirect("/billing_project/jsp/editOrDeleteUser.jsp");
    }
}