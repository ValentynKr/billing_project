package com.epam.billing.filter;

import com.epam.billing.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

public class AccessFilter implements Filter {

    private final List<String> availableForUser = asList(

            // list of user jsp pages
            "/billing_project/login.jsp",
            "/billing_project/jsp/registration.jsp",
            "/billing_project/jsp/changeUserInfo.jsp",
            "/billing_project/jsp/createRequestFromUser.jsp",
            "/billing_project/jsp/requestFormForCreate.jsp",
            "/billing_project/jsp/requestFormForDelete.jsp",
            "/billing_project/jsp/requestFormForEdit.jsp",
            "/billing_project/jsp/requestFormForInvolve.jsp",
            "/billing_project/jsp/welcome.jsp",
            "/billing_project/jsp/accessDenied.jsp",

            // list of user servlets
            "/billing_project/registration",
            "/billing_project/addUserActivityDuration",
            "/billing_project/changeUserInfo",
            "/billing_project/login",
            "/billing_project/logout",
            "/billing_project/userRequestCreateServlet",
            "/billing_project/userRequestDeleteServlet",
            "/billing_project/userRequestEditServlet",
            "/billing_project/userRequestHandlingServlet",
            "/billing_project/userRequestInvolveServlet",

            // bootstrap resource directories
            "/billing_project/css/signin.css",
            "/billing_project/css/bootstrap.min.css");

    private final List<String> availableForAdmin = asList(

            // list of user jsp pages
            "/billing_project/login.jsp",
            "/billing_project/jsp/registration.jsp",
            "/billing_project/jsp/activityEditingForm.jsp",
            "/billing_project/jsp/addActivityAdmin.jsp",
            "/billing_project/jsp/addCategoryAdmin.jsp",
            "/billing_project/jsp/addUserAdmin.jsp",
            "/billing_project/jsp/categoryEditingForm.jsp",
            "/billing_project/jsp/categoryEditingForm.jsp",
            "/billing_project/jsp/changeUserInfo.jsp",
            "/billing_project/jsp/createRequestFromUser.jsp",
            "/billing_project/jsp/editOrDeleteActivity.jsp",
            "/billing_project/jsp/editOrDeleteCategory.jsp",
            "/billing_project/jsp/editOrDeleteUser.jsp",
            "/billing_project/jsp/requestFormForCreate.jsp",
            "/billing_project/jsp/requestFormForDelete.jsp",
            "/billing_project/jsp/requestFormForEdit.jsp",
            "/billing_project/jsp/requestFormForInvolve.jsp",
            "/billing_project/jsp/userEditingForm.jsp",
            "/billing_project/jsp/userRequestEditForm.jsp",
            "/billing_project/jsp/userRequestForm.jsp",
            "/billing_project/jsp/userRequestsAdmin.jsp",
            "/billing_project/jsp/watchAllActivitiesAdmin.jsp",
            "/billing_project/jsp/watchAllActivitiesAdminFiltered.jsp",
            "/billing_project/jsp/watchAllUsersAdmin.jsp",
            "/billing_project/jsp/watchUserActivities.jsp",
            "/billing_project/jsp/watchAllUsersAdmin.jsp",
            "/billing_project/jsp/welcome-admin.jsp",
            "/billing_project/jsp/welcome.jsp",
            "/billing_project/jsp/accessDenied.jsp",

            // list of user servlets
            "/billing_project/registration",
            "/billing_project/addActivityAdmin",
            "/billing_project/addCategoryAdmin",
            "/billing_project/addUserActivityDuration",
            "/billing_project/addUserAdmin",
            "/billing_project/changeUserInfo",
            "/billing_project/deleteActivityAdmin",
            "/billing_project/deleteCategoryAdmin",
            "/billing_project/deleteUserAdmin",
            "/billing_project/editActivityAdminForm",
            "/billing_project/editActivityAdmin",
            "/billing_project/editCategoryAdminForm",
            "/billing_project/editCategoryAdmin",
            "/billing_project/editUserAdminForm",
            "/billing_project/editUserAdmin",
            "/billing_project/login",
            "/billing_project/logout",
            "/billing_project/userActivityInfoAdmin",
            "/billing_project/userRequestAcceptServlet",
            "/billing_project/userRequestCreateServlet",
            "/billing_project/userRequestDeclineServlet",
            "/billing_project/userRequestDeleteServlet",
            "/billing_project/userRequestEditServlet",
            "/billing_project/userRequestHandlingServlet",
            "/billing_project/userRequestInfoServlet",
            "/billing_project/userRequestInvolveServlet",
            "/billing_project/watchAllActivitiesServlet",
            "/billing_project/watchAllUserRequestsServlet",
            "/billing_project/downloadReport",

            // bootstrap resource directories
            "/billing_project/css/signin.css",
            "/billing_project/css/bootstrap.min.css");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        HttpSession session = ((HttpServletRequest) request).getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            chain.doFilter(request, response);
        }
        else if ((!user.isAdmin()) && (availableForUser.contains(requestURI))) {
            chain.doFilter(request, response);
        }
        else if ((user.isAdmin()) && (availableForAdmin.contains(requestURI))){
            chain.doFilter(request, response);
        } else {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/jsp/accessDenied.jsp");
        }
    }

    @Override
    public void destroy() {

    }
}
