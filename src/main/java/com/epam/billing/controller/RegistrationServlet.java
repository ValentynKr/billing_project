package com.epam.billing.controller;

import com.epam.billing.exeption.AppException;
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

@WebServlet(urlPatterns = {"/registration"})
public class RegistrationServlet extends HttpServlet {
    private UserService userService;


    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String password = req.getParameter("password");
        String confPassword = req.getParameter("confPassword");
        if (!password.equals(confPassword)) {
            req.getSession().setAttribute("Alert", "Password and confirmation don`t match");
        } else {
            if (ValidationUtil.isEmailValid(req.getParameter("email"))) { //toDo need to be refactored
                String email = req.getParameter("email");
                if (userService.getByEmail(email).isPresent()) {
                    req.getSession().setAttribute("Alert", "User with such email is already registered"); //toDo should be specified by locale
                } else {
                    if (ValidationUtil.isPasswordValid(req.getParameter("password"))) {
                        String name = req.getParameter("name");
                        User user = new User().setName(name).setEmail(email).setAdmin(false).setPassword(PasswordHashingUtil.getSaltedHash(password));
                        userService.save(user);
                        req.getSession().setAttribute("Alert", "Registration was accomplished. Thank you!");
                    } else {
                        req.getSession().setAttribute("Alert", "Chosen pass is invalid. It should contain at least 8 symbols, including number, letter and special symbol");
                    }
                }
            } else {
                req.getSession().setAttribute("Alert", "Chosen email is invalid. It should`n contain special symbols or cyrillic letters. Example - ggg@gmail.com");
            }
        }
        req.getRequestDispatcher("/jsp/registration.jsp").forward(req, resp);
    }
}