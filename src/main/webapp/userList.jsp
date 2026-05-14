<%@ page import="models.User, java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<User> users = (List<User>) request.getAttribute("users");
    if (users == null) users = new java.util.ArrayList<>();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Users</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="navbar">
    <h1>🚗 AutoTrade Hub</h1>
    <div class="nav-links"><a href="index.jsp">Home</a><a href="profile.jsp">Profile</a><a href="logout">Logout</a></div>
</div>
<div class="container">
    <div class="card">
        <h2>All Registered Users</h2>
        <table><th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Type</th><th>Admin</th></tr>
            <% for(User u: users) { %>
            <tr><td><%= u.getId() %></td><td><%= u.getName() %></td><td><%= u.getEmail() %></td><td><%= u.getPhone() %></td><td><%= u.getUserType() %></td><td><%= u.isAdmin() ? "Yes" : "No" %></td></tr>
            <% } %>
        </table>
    </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>