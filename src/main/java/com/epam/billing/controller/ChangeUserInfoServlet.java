package com.epam.billing.controller;

import com.epam.billing.entity.User;
import com.epam.billing.exeption.AppException;
import com.epam.billing.service.*;
import com.epam.billing.utils.PasswordHashingUtil;
import com.epam.billing.utils.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/changeUserInfo"})
public class ChangeUserInfoServlet extends HttpServlet {
    private UserService userService;


    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        String password = req.getParameter("password");

        if (password.isEmpty()) {
            password = user.getPassword();
            if (ValidationUtil.isEmailValid(req.getParameter("email"))) { //toDo need to be refactored
                String email = req.getParameter("email");
                String name = req.getParameter("name");
                User newUser = new User().setUserId(user.getUserId()).setName(name).setEmail(email).setAdmin(false)
                        .setPassword(password);
                userService.update(newUser);
                req.getSession().setAttribute("user", newUser);
                req.getSession().setAttribute("Alert", "Your user info updated");
            } else {
                req.getSession().setAttribute("Alert", "Chosen email is invalid. It should`n contain special symbols or cyrillic letters. Example - ggg@gmail.com");
            }
        } else {
            if (ValidationUtil.isEmailValid(req.getParameter("email"))) { //toDo need to be refactored
                if (ValidationUtil.isPasswordValid(req.getParameter("password"))) {
                    String name = req.getParameter("name");
                    String email = req.getParameter("email");
                    User newUser = null;
                    try {
                        newUser = new User().setUserId(user.getUserId()).setName(name).setEmail(email).setAdmin(false)
                                .setPassword(PasswordHashingUtil.getSaltedHash(password));
                    } catch (AppException e) {
                        e.printStackTrace(); //toDo need to operate the exception
                    }
                    userService.update(newUser);
                    req.getSession().setAttribute("user", newUser);
                    req.getSession().setAttribute("Alert", "Your user info updated");
                } else {
                    req.getSession().setAttribute("Alert", "Chosen pass is invalid. It should contain at least 8 symbols, including number, letter and special symbol");
                }
            } else {
                req.getSession().setAttribute("Alert", "Chosen email is invalid. It should`n contain special symbols or cyrillic letters. Example - ggg@gmail.com");
            }
        }
        req.getRequestDispatcher("/jsp/changeUserInfo.jsp").forward(req, resp);
    }
}
