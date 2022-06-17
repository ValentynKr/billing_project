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

@WebServlet(urlPatterns = {"/registration"})
public class RegistrationServlet extends HttpServlet {
    public static final String CONFIRMATION_DON_T_MATCH = "Password and confirmation don`t match";
    public static final String SUCH_EMAIL_IS_ALREADY_REGISTERED = "User with such email is already registered";
    public static final String REGISTRATION_ACCOMPLISHED_MESSAGE = "Registration was accomplished. Thank you!";
    public static final String PASS_INVALID_MESSAGE = "Chosen pass is invalid. It should contain at least 8 symbols, including number, letter and special symbol";
    public static final String EMAIL_INVALID_MESSAGE = "Chosen email is invalid. It should`n contain special symbols or cyrillic letters. Example - ggg@gmail.com";
    private UserService userService;


    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String password = req.getParameter("password");
        String confPassword = req.getParameter("confPassword");
        String email = req.getParameter("email");
        String name = req.getParameter("name");

        if (!password.equals(confPassword)) {
            req.getSession().setAttribute("Alert", CONFIRMATION_DON_T_MATCH);
        } else {
            if (ValidationUtil.isEmailValid(email)) {
                if (userService.getByEmail(email).isPresent()) {
                    req.getSession().setAttribute("Alert", SUCH_EMAIL_IS_ALREADY_REGISTERED);
                } else {
                    if (ValidationUtil.isPasswordValid(password)) {
                        createUser(password, email, name);
                        req.getSession().setAttribute("Alert", REGISTRATION_ACCOMPLISHED_MESSAGE);
                    } else {
                        req.getSession().setAttribute("Alert", PASS_INVALID_MESSAGE);
                    }
                }
            } else {
                req.getSession().setAttribute("Alert", EMAIL_INVALID_MESSAGE);
            }
        }
        req.getRequestDispatcher("/jsp/registration.jsp").forward(req, resp);
    }

    private void createUser(String password, String email, String name) {
        User user = new User()
                .setName(name)
                .setEmail(email)
                .setAdmin(false)
                .setPassword(PasswordHashingUtil.getSaltedHash(password));
        userService.save(user);
    }
}