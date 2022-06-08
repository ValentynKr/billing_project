package com.epam.billing.controller;

import com.epam.billing.utils.PasswordHashingUtil;
import com.epam.billing.utils.ValidationUtil;
import com.epam.billing.entity.User;
import com.epam.billing.service.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/editUserAdmin"})
public class EditUserAdminServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int userId = Integer.parseInt(req.getParameter("userIdToEdit"));
        User user = userService.getById(userId);
        req.getSession().setAttribute("userToEdit", user);
        req.getRequestDispatcher("/jsp/userEditingForm.jsp").forward(req, resp);
    }
}