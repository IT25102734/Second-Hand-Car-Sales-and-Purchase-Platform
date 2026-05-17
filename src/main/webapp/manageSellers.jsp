<%@ page import="models.User, models.SellerUser, java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User admin = (User) session.getAttribute("loggedInUser");
    if (admin == null || !admin.isAdmin()) response.sendRedirect("login.jsp");
    List<User> sellers = (List<User>) request.getAttribute("sellers");
    if (sellers == null) sellers = new java.util.ArrayList<>();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Sellers</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="navbar">
    <h1>🚗 AutoTrade Hub</h1>
    <div class="nav-links"><a href="index.jsp">Home</a><a href="manageSellers">Manage Sellers</a><a href="profile.jsp">Profile</a><a href="logout">Logout</a></div>
</div>
<div class="container">
    <div class="card">
        <h2>All Sellers</h2>
        <tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Dealership</th><th>Years</th><th>Actions</th></tr>
        <% if(sellers.isEmpty()) { %><tr><td colspan="7">No sellers.</td></tr><% } else { for(User s: sellers) { SellerUser su = (SellerUser)s; %>
        <tr><td><%= su.getId() %></td><td><%= su.getName() %></td><td><%= su.getEmail() %></td><td><%= su.getPhone() %></td><td><%= su.getDealershipName() %></td><td><%= su.getYearsInBusiness() %></td><td><a href="editSeller?id=<%= su.getId() %>" class="btn">Edit</a><form action="deleteSeller" method="post" style="display:inline;" onsubmit="return confirm('Delete?');"><input type="hidden" name="id" value="<%= su.getId() %>"><button type="submit" class="btn btn-danger">Delete</button></form></td></tr>
        <% } } %>
        </table>
    </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>