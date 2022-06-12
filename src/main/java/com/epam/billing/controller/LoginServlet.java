package com.epam.billing.controller;

import com.epam.billing.entity.Language;
import com.epam.billing.entity.User;
import com.epam.billing.exeption.AppException;
import com.epam.billing.service.*;
import com.epam.billing.utils.PasswordHashingUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private UserService userService;
    private UserActivityService userActivityService;
    private LanguageService languageService;
    private ActivityCategoryService activityCategoryService;
    private ActivityService activityService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        userActivityService = (UserActivityService) getServletContext().getAttribute("userActivityService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        activityCategoryService = (ActivityCategoryService) getServletContext().getAttribute("activityCategoryService");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getSession().getAttribute("language").toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login").trim();
        String password = req.getParameter("password");
        Optional<User> userOptional = userService.getByEmail(login);
        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());

        if (!userOptional.isPresent()) {
            req.getSession().setAttribute("Alert", "User with such email is not registered. Please, register!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            try {
                if (PasswordHashingUtil.check(password, user.getPassword())) {
                    HttpSession session = req.getSession();
                    session.setAttribute("user", user);
                    if (user.isAdmin()) {

                        req.getSession().setAttribute("userActivities",
                                userActivityService.getAllUserActivitiesDurationDTO(language.getId()));
                        req.getSession().setAttribute("listOfAllUsers", userService.getAll());
                        req.getSession().setAttribute("listOfOpenedActivityCategories", activityCategoryService.getOpenedWithLocalizedNames(language.getId()));
                        req.getSession().setAttribute("listOfAllActivityCategories", activityCategoryService.getAllWithLocalizedNames(language.getId()));
                        req.getSession().setAttribute("listOfAllActivityCategoriesWithStatus", activityCategoryService.getAllWithLocalizedNameStatusDTO(language.getId()));
                        req.getSession().setAttribute("listOfAllActivities", activityService.getAll());
                        req.getSession().setAttribute("listOfAllActivitiesWithLocalizedCategories", activityService.getAllWithCategoryLocalizedNames(language.getId()));

                        req.getRequestDispatcher("/jsp/welcome-admin.jsp").forward(req, resp);
                    } else {
                        req.getSession().setAttribute("userActivities",
                                userActivityService.getUserActivityUserNameIdDurationDTO(user.getUserId(), language.getId()));
                        resp.sendRedirect("/billing_project/jsp/welcome.jsp");
                    }
                } else {
                    req.getSession().setAttribute("Alert", "Wrong password!");
                    req.getRequestDispatcher("login.jsp").forward(req, resp);
                }
            } catch (AppException e) {
                e.printStackTrace();
            }
        }
    }
}
