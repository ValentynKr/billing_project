package com.epam.billing.controller;

import com.epam.billing.entity.Language;
import com.epam.billing.service.ActivityService;
import com.epam.billing.service.LanguageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/userRequestHandlingServlet"})
public class UserRequestHandlingServlet extends HttpServlet {
    private ActivityService activityService;
    private LanguageService languageService;

    private Map<String, String> typeOfRequestToJspPath;

    @Override
    public void init() {
        initRequestTypePathMap();
        activityService = (ActivityService) getServletContext().getAttribute("activityService");
        languageService = (LanguageService) getServletContext().getAttribute("languageService");
    }

    private void initRequestTypePathMap() {
        typeOfRequestToJspPath = new HashMap<>();
        typeOfRequestToJspPath.put("CREATE", "/jsp/requestFormForCreate.jsp");
        typeOfRequestToJspPath.put("INVOLVE", "/jsp/requestFormForInvolve.jsp");
        typeOfRequestToJspPath.put("EDIT", "/jsp/requestFormForEdit.jsp");
        typeOfRequestToJspPath.put("DELETE", "/jsp/requestFormForDelete.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Language language = languageService.getByShortName(req.getSession().getAttribute("language").toString());
        String typeOfRequest = req.getParameter("typeOfRequest");
        String path = typeOfRequestToJspPath.get(typeOfRequest);
        if (typeOfRequest.equals("INVOLVE")) {
            req.getSession().setAttribute("listOfAllActivitiesWithLocalizedCategories", activityService.getAllWithCategoryLocalizedNames(language.getId()));
        }
        req.getRequestDispatcher(path).forward(req, resp);
    }
}
