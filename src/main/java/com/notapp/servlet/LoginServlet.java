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

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        UserDao userDao = new UserDao();
        User user = userDao.getUserByEmailAndPassword(email, password);
        
        HttpSession session = request.getSession();
        
        if (user != null) {
            session.setAttribute("currentUser", user);
            response.sendRedirect("home.jsp");
        } else {
            session.setAttribute("message", "Invalid email or password!");
            response.sendRedirect("login.jsp");
        }
    }
}