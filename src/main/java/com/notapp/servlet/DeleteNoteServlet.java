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

public class DeleteNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int noteId = Integer.parseInt(request.getParameter("noteId"));
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        NoteDao noteDao = new NoteDao();
        Note note = noteDao.getNoteById(noteId);
        
        if (note != null && note.getUser().getId() == user.getId()) {
            boolean deleted = noteDao.deleteNote(noteId);
            
            if (deleted) {
                session.setAttribute("message", "Note deleted successfully!");
            } else {
                session.setAttribute("message", "Failed to delete note!");
            }
        } else {
            session.setAttribute("message", "Unauthorized access!");
        }
        
        response.sendRedirect("home.jsp");
    }
}