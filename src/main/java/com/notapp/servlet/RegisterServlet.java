package com.notapp.servlet;

import com.notapp.dao.UserDao;
import com.notapp.entity.User;
import com.notapp.helper.FactoryProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        UserDao userDao = new UserDao();
        
        // Check if user already exists
        if (userDao.getUserByEmail(email) != null) {
            HttpSession session = request.getSession();
            session.setAttribute("message", "Email already exists! Please login.");
            response.sendRedirect("register.jsp");
            return;
        }
        
        User user = new User(email, password, name);
        boolean saved = userDao.saveUser(user);
        
        HttpSession session = request.getSession();
        
        if (saved) {
            session.setAttribute("message", "Registration successful! Please login.");
            response.sendRedirect("login.jsp");
        } else {
            session.setAttribute("message", "Registration failed! Please try again.");
            response.sendRedirect("register.jsp");
        }
    }
}