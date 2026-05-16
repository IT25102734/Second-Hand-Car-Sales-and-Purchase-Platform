<%@ page import="models.Brand, models.DomesticBrand, models.ImportedBrand, models.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  User user = (User) session.getAttribute("loggedInUser");
  if (user == null || !user.isAdmin()) response.sendRedirect("login.jsp");
  Brand brand = (Brand) request.getAttribute("brand");
  if (brand == null) response.sendRedirect("brands");
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Brand</title>
  <link rel="stylesheet" href="css/style.css">
  <script>
    function showExtra() {
      var type = document.getElementById("type").value;
      document.getElementById("domestic").style.display = (type === "domestic") ? "block" : "none";
      document.getElementById("imported").style.display = (type === "imported") ? "block" : "none";
    }
  </script>
</head>
<body onload="showExtra()">
<div class="navbar">
  <h1>🚗 AutoTrade Hub</h1>
  <div class="nav-links"><a href="index.jsp">Home</a><a href="brands">Brands</a><a href="profile.jsp">Profile</a><a href="logout">Logout</a></div>
</div>
<div class="container">
  <div class="card">
    <h2>Edit Brand: <%= brand.getName() %></h2>
    <form action="editBrand" method="post">
      <input type="hidden" name="id" value="<%= brand.getId() %>">
      <div class="form-group"><label>Name</label><input type="text" name="name" value="<%= brand.getName() %>" required></div>
      <div class="form-group"><label>Country</label><input type="text" name="country" value="<%= brand.getCountry() %>" required></div>
      <div class="form-group"><label>Type</label><select id="type" name="type" onchange="showExtra()"><option value="domestic" <%= "domestic".equals(brand.getType())?"selected":"" %>>Domestic</option><option value="imported" <%= "imported".equals(brand.getType())?"selected":"" %>>Imported</option></select></div>
      <div id="domestic" style="<%= "domestic".equals(brand.getType())?"block":"none" %>"><div class="form-group"><label>Manufacturing Plants</label><input type="number" name="plants" value="<%= (brand.getType().equals("domestic")) ? ((DomesticBrand)brand).getLocalManufacturingPlants() : "" %>"></div></div>
      <div id="imported" style="<%= "imported".equals(brand.getType())?"block":"none" %>"><div class="form-group"><label>Import Duty (%)</label><input type="number" step="0.1" name="duty" value="<%= (brand.getType().equals("imported")) ? ((ImportedBrand)brand).getImportDutyPercentage() : "" %>"></div></div>
      <button type="submit" class="btn">Save</button>
    </form>
  </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>