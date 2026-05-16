<%@ page import="models.Brand, models.DomesticBrand, models.ImportedBrand, models.User, java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  List<Brand> brands = (List<Brand>) request.getAttribute("brands");
  if (brands == null) brands = new java.util.ArrayList<>();
  User user = (User) session.getAttribute("loggedInUser");
  boolean canManage = (user != null);
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Car Brands</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="navbar">
  <h1>🚗 AutoTrade Hub</h1>
  <div class="nav-links">
    <a href="index.jsp">Home</a>
    <a href="cars">Browse Cars</a>
    <% if(user != null) { if("buyer".equals(user.getUserType())) { %><a href="myRequests">My Requests</a><a href="profile.jsp">Profile</a><% } else if("seller".equals(user.getUserType())) { %><a href="sellerDashboard.jsp">Dashboard</a><a href="myListings">My Listings</a><a href="sellerRequests">Requests</a><a href="profile.jsp">Profile</a><% } %><a href="brands">Brands</a><a href="logout">Logout</a><% } else { %><a href="login.jsp">Login</a><a href="register.jsp">Register</a><% } %>
  </div>
</div>
<div class="container">
  <div class="card">
    <h2>Car Brands</h2>
    <% if(canManage) { %><a href="addBrand.jsp" class="btn" style="margin-bottom:1rem;">Add New Brand</a><% } %>
    <table><th>ID</th><th>Name</th><th>Country</th><th>Type</th><th>Extra Info</th><% if(canManage) { %><th>Actions</th><% } %></tr>
      <% if(brands.isEmpty()) { %><tr><td colspan="6">No brands yet.</td></tr><% } else { for(Brand b: brands) { %>
      <tr><td><%= b.getId() %></td><td><%= b.getName() %></td><td><%= b.getCountry() %></td><td><%= b.getType() %></td><td><% if("domestic".equals(b.getType())) { %>Plants: <%= ((DomesticBrand)b).getLocalManufacturingPlants() %><% } else if("imported".equals(b.getType())) { %>Duty: <%= ((ImportedBrand)b).getImportDutyPercentage() %>% <% } else { %>-<% } %></td>
        <% if(canManage) { %><td><a href="editBrand?id=<%= b.getId() %>" class="btn">Edit</a><form action="deleteBrand" method="post" style="display:inline;" onsubmit="return confirm('Delete?');"><input type="hidden" name="id" value="<%= b.getId() %>"><button type="submit" class="btn btn-danger">Delete</button></form></td><% } %></tr>
      <% } } %>
    </table>
  </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>