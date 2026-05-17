<%@ page import="models.User, models.SellerUser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User admin = (User) session.getAttribute("loggedInUser");
    if (admin == null || !admin.isAdmin()) response.sendRedirect("login.jsp");
    User seller = (User) request.getAttribute("seller");
    if (seller == null || !"seller".equals(seller.getUserType())) response.sendRedirect("manageSellers");
    SellerUser su = (SellerUser) seller;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Seller</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="navbar">
    <h1>🚗 AutoTrade Hub</h1>
    <div class="nav-links"><a href="index.jsp">Home</a><a href="manageSellers">Manage Sellers</a><a href="profile.jsp">Profile</a><a href="logout">Logout</a></div>
</div>
<div class="container">
    <div class="card">
        <h2>Edit Seller: <%= su.getName() %></h2>
        <form action="editSeller" method="post">
            <input type="hidden" name="id" value="<%= su.getId() %>">
            <div class="form-group"><label>Name</label><input type="text" name="name" value="<%= su.getName() %>" required></div>
            <div class="form-group"><label>Email</label><input type="email" name="email" value="<%= su.getEmail() %>" required></div>
            <div class="form-group"><label>Phone</label><input type="text" name="phone" value="<%= su.getPhone() %>" required></div>
            <button type="submit" class="btn">Save</button>
        </form>
    </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>