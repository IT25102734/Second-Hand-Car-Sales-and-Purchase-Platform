<%@ page import="models.Car, models.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  Car car = (Car) request.getAttribute("car");
  if (car == null) {
    // If car not set in request, try to get from parameter
    String carIdParam = request.getParameter("carId");
    if (carIdParam != null) {
      response.sendRedirect("carDetails?id=" + carIdParam);
      return;
    } else {
      response.sendRedirect("cars");
      return;
    }
  }
  User loggedUser = (User) session.getAttribute("loggedInUser");
  if (loggedUser == null || !"buyer".equals(loggedUser.getUserType())) {
    response.sendRedirect("login.jsp");
    return;
  }
%>
<!DOCTYPE html>
<html>
<head>
  <title>Send Purchase Request - AutoTrade Hub</title>
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
    <h2>Request to Buy: <%= car.getTitle() %></h2>
    <p><strong>Price:</strong> $<%= car.getPrice() %></p>
    <form action="sendRequest" method="post">
      <input type="hidden" name="carId" value="<%= car.getId() %>">
      <div class="form-group">
        <label>Message to Seller</label>
        <textarea name="message" rows="4" required placeholder="I am interested in this car..."></textarea>
      </div>
      <button type="submit" class="btn">Send Request</button>
      <a href="cars" class="btn btn-outline">Cancel</a>
    </form>
  </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>