<%@ page import="models.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  User user = (User) session.getAttribute("loggedInUser");
  if (user == null || !user.isAdmin()) response.sendRedirect("login.jsp");
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Add Brand</title>
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
    <h2>Add New Brand</h2>
    <form action="addBrand" method="post">
      <div class="form-group"><label>Name</label><input type="text" name="name" required></div>
      <div class="form-group"><label>Country</label><input type="text" name="country" required></div>
      <div class="form-group"><label>Type</label><select id="type" name="type" onchange="showExtra()"><option value="domestic">Domestic</option><option value="imported">Imported</option></select></div>
      <div id="domestic"><div class="form-group"><label>Manufacturing Plants</label><input type="number" name="plants" value="1"></div></div>
      <div id="imported" style="display:none"><div class="form-group"><label>Import Duty (%)</label><input type="number" step="0.1" name="duty" value="0.0"></div></div>
      <button type="submit" class="btn">Add Brand</button>
    </form>
  </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>