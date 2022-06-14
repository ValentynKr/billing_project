package com.epam.billing.controller;

import com.epam.billing.entity.*;
import com.epam.billing.service.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/userRequestDeclineServlet"})
public class UserRequestDeclineServlet extends HttpServlet {
    private UserRequestService userRequestService;

    @Override
    public void init() {
        userRequestService = (UserRequestService) getServletContext().getAttribute("userRequestService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserRequest userRequest = ((UserRequest) req.getSession().getAttribute("userRequest"));
        userRequest.setRequestStatus(RequestStatus.CANCELED);
        userRequestService.update(userRequest);
        req.getSession().setAttribute("listOfUserRequests", userRequestService.getAllWithUserNames());
        resp.sendRedirect("/billing_project/jsp/userRequestsAdmin.jsp");
    }
}
