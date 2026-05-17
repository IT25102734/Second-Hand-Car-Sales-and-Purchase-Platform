<%@ page import="models.PurchaseRequest, models.Car, models.User, utils.CarService, java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%
  User loggedUser = (User) session.getAttribute("loggedInUser");
  if (loggedUser == null || !"buyer".equals(loggedUser.getUserType())) {
    response.sendRedirect("login.jsp");
    return;
  }
  List<PurchaseRequest> requests = (List<PurchaseRequest>) request.getAttribute("requests");
  if (requests == null) requests = new ArrayList<>();
  CarService carService = new CarService();
%>
<!DOCTYPE html>
<html>
<head>
  <title>My Requests - AutoTrade Hub</title>
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
    <h2>My Purchase Requests</h2>
    <% if (request.getParameter("sent") != null) { %>
    <div class="success">Request sent successfully!</div>
    <% } %>
    <% if (requests.isEmpty()) { %>
    <p>You haven't sent any requests yet. <a href="cars">Browse cars</a> to make an offer.</p>
    <% } else { %>
    <table>
      <tr><th>Car</th><th>Message</th><th>Date</th><th>Status</th></tr>
      <% for (PurchaseRequest req : requests) {
        Car car = carService.getCarById(req.getCarId());
        String carName = (car != null) ? car.getTitle() : "Unknown";
      %>
      <tr>
        <td><%= carName %></td>
        <td><%= req.getMessage() %></td>
        <td><%= req.getRequestDate() %></td>
        <td>
          <% if ("pending".equals(req.getStatus())) { %>
          <span style="color: orange;">Pending</span>
          <% } else if ("accepted".equals(req.getStatus())) { %>
          <span style="color: green;">Accepted</span>
          <% } else { %>
          <span style="color: red;">Rejected</span>
          <% } %>
        </td>
      </tr>
      <% } %>
    </table>
    <% } %>
  </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>