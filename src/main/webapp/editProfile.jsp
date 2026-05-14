<%@ page import="models.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User user = (User) session.getAttribute("loggedInUser");
    if(user == null) response.sendRedirect("login.jsp");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Profile</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="navbar">
    <h1>🚗 AutoTrade Hub</h1>
    <div class="nav-links">
        <a href="profile.jsp">Cancel</a>
        <a href="logout">Logout</a>
    </div>
</div>
<div class="container">
    <div class="form-container" style="max-width:600px; margin:0 auto;">
        <h2>Edit Profile</h2>
        <% if(request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
        <form action="editProfile" method="post">
            <div class="form-group"><label>Name</label><input type="text" name="name" value="<%= user.getName() %>" required></div>
            <div class="form-group"><label>Email</label><input type="email" name="email" value="<%= user.getEmail() %>" required></div>
            <div class="form-group"><label>Password</label><input type="password" name="password" value="<%= user.getPassword() %>" required></div>
            <div class="form-group"><label>Phone</label><input type="text" name="phone" value="<%= user.getPhone() %>" required></div>
            <button type="submit" class="btn">Save Changes</button>
        </form>
    </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>