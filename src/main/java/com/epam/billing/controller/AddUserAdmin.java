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

@WebServlet(urlPatterns = {"/addUserAdmin"})
public class AddUserAdmin extends HttpServlet {
    private static final String EMAIL_IS_INVALID_MESSAGE = "Chosen email is invalid. It should`n contain special symbols or cyrillic letters. Example - ggg@gmail.com";
    private static final String PASSWORD_IS_INVALID_MESSAGE = "Chosen pass is invalid. It should contain at least 8 symbols, including number, letter and special symbol";
    private UserService userService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String password = req.getParameter("password");
        int isAdminNum = Integer.parseInt(req.getParameter("isAdmin"));
        boolean isAdmin;
        isAdmin = isAdminNum > 0;
        if (ValidationUtil.isEmailValid(req.getParameter("email"))) { //toDo need to be refactored
            String email = req.getParameter("email");
            if (userService.getByEmail(email).isPresent()) {
                req.getSession().setAttribute("Alert", "User with such email already exists"); //toDo should be specified by locale
            } else {
                if (ValidationUtil.isPasswordValid(req.getParameter("password"))) {
                    String name = req.getParameter("name");
                    User user = new User().setName(name).setEmail(email).setAdmin(isAdmin).setPassword(PasswordHashingUtil.getSaltedHash(password));
                    userService.save(user);
                    req.getSession().setAttribute("Alert", "User added");
                } else {
                    req.getSession().setAttribute("Alert", PASSWORD_IS_INVALID_MESSAGE);
                }
            }
        } else {
            req.getSession().setAttribute("Alert", EMAIL_IS_INVALID_MESSAGE);
        }
        req.getSession().setAttribute("listOfAllUsers", userService.getAll());
        req.getRequestDispatcher("/jsp/addUserAdmin.jsp").forward(req, resp);
    }
}