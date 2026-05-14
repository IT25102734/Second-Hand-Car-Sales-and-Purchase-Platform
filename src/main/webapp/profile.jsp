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
    <title>My Profile</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="navbar">
    <h1>🚗 AutoTrade Hub</h1>
    <div class="nav-links">
        <a href="index.jsp">Home</a>
        <% if("buyer".equals(user.getUserType())) { %>
        <a href="cars">Browse Cars</a>
        <a href="myRequests">My Requests</a>
        <% } else if("seller".equals(user.getUserType())) { %>
        <a href="sellerDashboard.jsp">Dashboard</a>
        <a href="myListings">My Listings</a>
        <a href="sellerRequests">Requests</a>
        <% } %>
        <a href="profile.jsp">Profile</a>
        <a href="logout">Logout</a>
    </div>
</div>
<div class="container">
    <div class="card">
        <h2>My Profile</h2>
        <% if(request.getParameter("updated") != null) { %>
        <div class="success">Profile updated successfully!</div>
        <% } %>
        <p><strong>ID:</strong> <%= user.getId() %></p>
        <p><strong>Name:</strong> <%= user.getName() %></p>
        <p><strong>Email:</strong> <%= user.getEmail() %></p>
        <p><strong>Phone:</strong> <%= user.getPhone() %></p>
        <p><strong>User Type:</strong> <%= user.getUserType() %></p>
        <a href="editProfile" class="btn">Edit Profile</a>
        <form action="deleteAccount" method="post" style="display:inline;" onsubmit="return confirm('Permanently delete your account?');">
            <button type="submit" class="btn btn-danger">Delete Account</button>
        </form>
        <br><br>
        <a href="userList">View All Users</a>
    </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>