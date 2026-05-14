<%@ page import="models.User, models.BuyerUser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User user = (User) session.getAttribute("loggedInUser");
    if(user == null || !"buyer".equals(user.getUserType())) response.sendRedirect("login.jsp");
    BuyerUser buyer = (BuyerUser) user;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Buyer Dashboard</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="navbar">
    <h1>🚗 AutoTrade Hub</h1>
    <div class="nav-links">
        <a href="index.jsp">Home</a>
        <a href="cars">Browse Cars</a>
        <a href="myRequests">My Requests</a>
        <a href="profile.jsp">Profile</a>
        <a href="logout">Logout</a>
    </div>
</div>
<div class="container">
    <div class="card">
        <h2>Welcome, <%= buyer.getName() %>!</h2>
        <p><strong>Budget:</strong> $<%= buyer.getBudget() %></p>
        <p><strong>Preferred Brand:</strong> <%= buyer.getPreferredBrand() %></p>
        <a href="cars" class="btn">Browse Cars</a>
    </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>