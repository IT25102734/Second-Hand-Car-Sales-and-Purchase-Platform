<%@ page import="models.Car, models.User, models.Brand, java.util.List, utils.BrandService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  List<Car> cars = (List<Car>) request.getAttribute("cars");
  if (cars == null) cars = new java.util.ArrayList<>();
  List<Brand> brands = (List<Brand>) request.getAttribute("brands");
  if (brands == null) brands = new java.util.ArrayList<>();
  User loggedUser = (User) session.getAttribute("loggedInUser");
  boolean isBuyer = (loggedUser != null && "buyer".equals(loggedUser.getUserType()));
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>All Cars</title>
  <link rel="stylesheet" href="css/style.css">
  <style>
    .status-available { color: green; font-weight: bold; }
    .status-sold { color: red; font-weight: bold; }
    table th, table td { border: 1px solid #ddd; padding: 8px; text-align: left; }
    table th { background-color: #1e2a3a; color: white; }
  </style>
</head>
<body>
<div class="navbar">
  <h1>🚗 AutoTrade Hub</h1>
  <div class="nav-links">
    <a href="index.jsp">Home</a>
    <a href="cars">Browse Cars</a>
    <% if(loggedUser != null) {
      if("buyer".equals(loggedUser.getUserType())) { %>
    <a href="myRequests">My Requests</a>
    <a href="profile.jsp">Profile</a>
    <% } else if("seller".equals(loggedUser.getUserType())) { %>
    <a href="sellerDashboard.jsp">Dashboard</a>
    <a href="myListings">My Listings</a>
    <a href="sellerRequests">Requests</a>
    <a href="profile.jsp">Profile</a>
    <% } %>
    <a href="brands">Brands</a>
    <a href="logout">Logout</a>
    <% } else { %>
    <a href="login.jsp">Login</a>
    <a href="register.jsp">Register</a>
    <% } %>
  </div>
</div>
<div class="container">
  <div class="card">
    <h2>All Cars</h2>
    <form method="get" action="cars" style="margin-bottom:2rem; display:flex; gap:1rem; flex-wrap:wrap; align-items:flex-end;">
      <div class="form-group" style="margin-bottom:0;">
        <label>Brand</label>
        <select name="brandId"><option value="">All Brands</option>
          <% for(Brand b: brands) {
            String sel = (request.getParameter("brandId") != null && Integer.parseInt(request.getParameter("brandId"))==b.getId())?"selected":"";
          %><option value="<%= b.getId() %>" <%= sel %>><%= b.getName() %></option><% } %>
        </select>
      </div>
      <div class="form-group" style="margin-bottom:0;"><label>Make</label><input type="text" name="make" value="<%= request.getParameter("make")!=null?request.getParameter("make"):"" %>"></div>
      <div class="form-group" style="margin-bottom:0;"><label>Model</label><input type="text" name="model" value="<%= request.getParameter("model")!=null?request.getParameter("model"):"" %>"></div>
      <div class="form-group" style="margin-bottom:0;"><label>Min Price</label><input type="number" name="minPrice" value="<%= request.getParameter("minPrice")!=null?request.getParameter("minPrice"):"" %>"></div>
      <div class="form-group" style="margin-bottom:0;"><label>Max Price</label><input type="number" name="maxPrice" value="<%= request.getParameter("maxPrice")!=null?request.getParameter("maxPrice"):"" %>"></div>
      <button type="submit" class="btn">Search</button>
    </form>
    <table>
      <thead><tr><th>Title</th><th>Make/Model</th><th>Year</th><th>Price</th><th>Mileage</th><th>Type</th><th>Status</th><th>Action</th></tr></thead>
      <tbody>
      <% if(cars.isEmpty()) { %><tr><td colspan="8">No cars found.</td></tr>
      <% } else { for(Car c: cars) { %>
      <tr>
        <td><%= c.getTitle() %></td><td><%= c.getMake() %> <%= c.getModel() %></td>
        <td><%= c.getYear() %></td><td>$<%= String.format("%.2f", c.getPrice()) %></td>
        <td><%= c.getMileage() %> mi</td><td><%= c.getType() %></td>
        <td class="<%= "available".equals(c.getStatus())?"status-available":"status-sold" %>"><%= c.getStatus() %></td>
        <td>
          <a href="carDetails?id=<%= c.getId() %>" class="btn">View</a>
          <% if(isBuyer && "available".equals(c.getStatus())) { %>
          <a href="sendRequest?carId=<%= c.getId() %>" class="btn">Request to Buy</a>
          <% } %>
        </td>
      </tr>
      <% } } %>
      </tbody>
    </table>
  </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>