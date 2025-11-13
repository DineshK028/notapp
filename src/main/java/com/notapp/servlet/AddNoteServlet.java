package com.notapp.servlet;

import com.notapp.dao.NoteDao;
import com.notapp.entity.Note;
import com.notapp.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class AddNoteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        Note note = new Note(title, content, user);
        NoteDao noteDao = new NoteDao();
        boolean saved = noteDao.saveNote(note);
        
        if (saved) {
            session.setAttribute("message", "Note added successfully!");
        } else {
            session.setAttribute("message", "Failed to add note!");
        }
        
        response.sendRedirect("home.jsp");
    }
}