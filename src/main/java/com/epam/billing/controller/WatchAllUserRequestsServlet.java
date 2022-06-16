package com.epam.billing.controller;

import com.epam.billing.dto.ActivityCategoryLocActivityUserActivityCountDTO;
import com.epam.billing.dto.DateStatusTypeUserRequestDTO;
import com.epam.billing.entity.*;
import com.epam.billing.service.ActivityCategoryService;
import com.epam.billing.service.ActivityService;
import com.epam.billing.service.LanguageService;
import com.epam.billing.service.UserRequestService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@WebServlet(urlPatterns = {"/watchAllUserRequestsServlet"})
public class WatchAllUserRequestsServlet extends HttpServlet {
    private UserRequestService userRequestService;

    @Override
    public void init() {
        userRequestService = (UserRequestService) getServletContext().getAttribute("userRequestService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 7;
        if (req.getParameter("page") != null)
            page = Integer.parseInt(
                    req.getParameter("page"));
        List<DateStatusTypeUserRequestDTO> requestList = userRequestService.getAllWithUserNamesToDemonstrate((page - 1) * recordsPerPage, recordsPerPage);
        int noOfRecords = userRequestService.getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0
                / recordsPerPage);
        req.setAttribute("requestListToDemo", requestList);
        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("currentPage", page);
        req.getSession().setAttribute("listOfUserRequests", userRequestService.getAllWithUserNames());
        req.getRequestDispatcher("/jsp/userRequestsAdmin.jsp").forward(req, resp);
    }
}
