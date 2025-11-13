<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.notapp.entity.User" %>
<%@ page import="com.notapp.entity.Note" %>
<%@ page import="com.notapp.dao.NoteDao" %>
<%
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
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Note - Note App</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#"><i class="fas fa-sticky-note"></i> Note App</a>
            <div class="navbar-nav ms-auto">
                <a class="btn btn-outline-light" href="home.jsp">Back to Home</a>
            </div>
        </div>
    </nav>
    
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-header bg-primary text-white">
                        <h4 class="mb-0"><i class="fas fa-edit"></i> Edit Note</h4>
                    </div>
                    <div class="card-body">
                        <form action="UpdateNoteServlet" method="post">
                            <input type="hidden" name="noteId" value="<%= note.getId() %>">
                            <div class="mb-3">
                                <label for="title" class="form-label">Title</label>
                                <input type="text" class="form-control" id="title" name="title" 
                                       value="<%= note.getTitle() %>" required>
                            </div>
                            <div class="mb-3">
                                <label for="content" class="form-label">Content</label>
                                <textarea class="form-control" id="content" name="content" rows="10" required><%= note.getContent() %></textarea>
                            </div>
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="home.jsp" class="btn btn-secondary">Cancel</a>
                                <button type="submit" class="btn btn-primary">Update Note</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>