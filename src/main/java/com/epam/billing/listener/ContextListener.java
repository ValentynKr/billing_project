package com.epam.billing.listener;

import com.epam.billing.repository.*;
import com.epam.billing.service.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        initUserService(context);
        initActivityService(context);
        initActivityCategoryService(context);
        initUserActivityService(context);
        initUserRequestService(context);
        initLanguageService(context);
        initActivityCategoryDescriptionService(context);
    }

    private void initActivityCategoryService(ServletContext context) {
        ActivityCategoryRepository activityCategoryRepository = new ActivityCategoryRepository();
        ActivityCategoryService activityCategoryService = new ActivityCategoryService(activityCategoryRepository);
        context.setAttribute("activityCategoryService", activityCategoryService);
    }

    private void initUserActivityService(ServletContext context) {
        UserActivityRepository userActivityRepository = new UserActivityRepository();
        UserActivityService userActivityService = new UserActivityService(userActivityRepository);
        context.setAttribute("userActivityService", userActivityService);
    }

    private void initUserRequestService(ServletContext context) {
        UserRequestRepository userRequestRepository = new UserRequestRepository();
        UserRequestService userRequestService = new UserRequestService(userRequestRepository);
        context.setAttribute("userRequestService", userRequestService);
    }

    private void initActivityService(ServletContext context) {
        ActivityRepository activityRepository = new ActivityRepository();
        ActivityService activityService = new ActivityService(activityRepository);
        context.setAttribute("activityService", activityService);
    }
    private void initUserService(ServletContext context) {
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        context.setAttribute("userService", userService);
    }

    private void initLanguageService(ServletContext context) {
        LanguageRepository languageRepository = new LanguageRepository();
        LanguageService languageService = new LanguageService(languageRepository);
        context.setAttribute("languageService", languageService);
    }

    private void initActivityCategoryDescriptionService(ServletContext context) {
        ActivityCategoryDescriptionRepository activityCategoryDescriptionRepository = new ActivityCategoryDescriptionRepository();
        ActivityCategoryDescriptionService activityCategoryDescriptionService = new ActivityCategoryDescriptionService(activityCategoryDescriptionRepository);
        context.setAttribute("activityCategoryDescriptionService", activityCategoryDescriptionService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
