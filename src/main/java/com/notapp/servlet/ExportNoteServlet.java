package com.notapp.servlet;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.notapp.dao.NoteDao;
import com.notapp.entity.Note;
import com.notapp.entity.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

public class ExportNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        int noteId = Integer.parseInt(request.getParameter("noteId"));
        NoteDao noteDao = new NoteDao();
        Note note = noteDao.getNoteById(noteId);
        
        if (note == null || note.getUser().getId() != user.getId()) {
            response.sendRedirect("home.jsp");
            return;
        }
        
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", 
                "attachment; filename=\"Note_" + note.getId() + "_" + 
                System.currentTimeMillis() + ".pdf\"");
            
            Document document = new Document();
            OutputStream out = response.getOutputStream();
            PdfWriter.getInstance(document, out);
            document.open();
            
            // Create fonts - corrected method
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
            Paragraph title = new Paragraph(note.getTitle(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);
            
            Font dateFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.GRAY);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy 'at' HH:mm");
            Paragraph date = new Paragraph("Created: " + dateFormat.format(note.getAddedDate()), dateFont);
            date.setAlignment(Element.ALIGN_CENTER);
            date.setSpacingAfter(20);
            document.add(date);
            
            document.add(new Paragraph("_____________________________________________"));
            document.add(new Paragraph(" "));
            
            Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
            Paragraph content = new Paragraph(note.getContent(), contentFont);
            content.setAlignment(Element.ALIGN_LEFT);
            content.setSpacingAfter(10);
            document.add(content);
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            Font footerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY);
            Paragraph footer = new Paragraph("Exported from Note App", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);
            
            document.close();
            out.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", "Error generating PDF: " + e.getMessage());
            response.sendRedirect("home.jsp");
        }
    }
}