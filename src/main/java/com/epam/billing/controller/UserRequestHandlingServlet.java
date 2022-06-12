package com.epam.billing.controller;

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

    private Map<String, String> typeOfRequestToJspPath;

    @Override
    public void init() {
        initRequestTypePathMap();
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

        String typeOfRequest = req.getParameter("typeOfRequest");
        String path = typeOfRequestToJspPath.get(typeOfRequest);
        req.getRequestDispatcher(path).forward(req, resp);
    }
}
