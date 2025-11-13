<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.notapp.entity.User" %>
<%@ page import="com.notapp.entity.Note" %>
<%@ page import="com.notapp.dao.NoteDao" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) session.getAttribute("currentUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    NoteDao noteDao = new NoteDao();
    List<Note> notes = noteDao.getNotesByUser(user);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home - Note App</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .note-card {
            transition: transform 0.2s;
        }
        .note-card:hover {
            transform: translateY(-5px);
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="home.jsp"><i class="fas fa-sticky-note"></i> Note App</a>
            <div class="navbar-nav ms-auto">
                <span class="navbar-text me-3">Welcome, <%= user.getName() %></span>
                <a class="btn btn-outline-light" href="LogoutServlet">Logout</a>
            </div>
        </div>
    </nav>
    
    <div class="container mt-4">
        <%
            String message = (String) session.getAttribute("message");
            if (message != null) {
        %>
        <div class="alert alert-<%= message.contains("success") || message.contains("successful") ? "success" : "danger" %> alert-dismissible fade show" role="alert">
            <%= message %>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <%
                session.removeAttribute("message");
            }
        %>
        
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>My Notes</h2>
            <a href="addNote.jsp" class="btn btn-primary">
                <i class="fas fa-plus"></i> Add Note
            </a>
        </div>
        
        <div class="row">
            <%
                if (notes == null || notes.isEmpty()) {
            %>
            <div class="col-12">
                <div class="alert alert-info text-center">
                    <i class="fas fa-info-circle"></i> No notes yet. Add your first note!
                </div>
            </div>
            <%
                } else {
                    for (Note note : notes) {
            %>
            <div class="col-md-4 mb-4">
                <div class="card note-card shadow-sm h-100">
                    <div class="card-body">
                        <h5 class="card-title"><%= note.getTitle() %></h5>
                        <p class="card-text"><%= note.getContent().length() > 100 ? note.getContent().substring(0, 100) + "..." : note.getContent() %></p>
                        <small class="text-muted">
                            <i class="fas fa-calendar"></i> <%= new java.text.SimpleDateFormat("MMM dd, yyyy HH:mm").format(note.getAddedDate()) %>
                        </small>
                    </div>
                    <div class="card-footer bg-transparent">
                        <a href="editNote.jsp?noteId=<%= note.getId() %>" class="btn btn-sm btn-outline-primary">
                            <i class="fas fa-edit"></i> Edit
                        </a>
                        <a href="ExportNoteServlet?noteId=<%= note.getId() %>" class="btn btn-sm btn-outline-success">
                            <i class="fas fa-file-pdf"></i> Export PDF
                        </a>
                        <a href="DeleteNoteServlet?noteId=<%= note.getId() %>" class="btn btn-sm btn-outline-danger" 
                           onclick="return confirm('Are you sure you want to delete this note?')">
                            <i class="fas fa-trash"></i> Delete
                        </a>
                    </div>
                </div>
            </div>
            <%
                    }
                }
            %>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>