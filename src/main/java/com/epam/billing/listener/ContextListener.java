package com.epam.billing.listener;

import com.epam.billing.repository.ActivityCategoryRepository;
import com.epam.billing.repository.ActivityRepository;
import com.epam.billing.repository.UserRepository;
import com.epam.billing.service.ActivityCategoryService;
import com.epam.billing.service.ActivityService;
import com.epam.billing.service.UserService;

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
    }

    private void initActivityCategoryService(ServletContext context) {
        ActivityCategoryRepository activityCategoryRepository = new ActivityCategoryRepository();
        ActivityCategoryService activityCategoryService = new ActivityCategoryService(activityCategoryRepository);
        context.setAttribute("activityCategory", activityCategoryService);
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



    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
