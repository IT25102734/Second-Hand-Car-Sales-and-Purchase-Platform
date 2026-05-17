<%@ page import="models.PurchaseRequest, models.Car, models.User, utils.CarService, java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%
  User loggedUser = (User) session.getAttribute("loggedInUser");
  if (loggedUser == null || !"seller".equals(loggedUser.getUserType())) {
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
  <title>Incoming Requests - AutoTrade Hub</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="navbar">
  <h1>🚗 AutoTrade Hub</h1>
  <div class="nav-links">
    <a href="index.jsp">Home</a>
    <a href="sellerDashboard.jsp">Dashboard</a>
    <a href="myListings">My Listings</a>
    <a href="sellerRequests">Requests</a>
    <a href="profile.jsp">Profile</a>
    <a href="logout">Logout</a>
  </div>
</div>
<div class="container">
  <div class="card">
    <h2>Purchase Requests (Incoming)</h2>
    <% if (requests.isEmpty()) { %>
    <p>No requests yet.</p>
    <% } else { %>
    <table>
      <tr><th>Car</th><th>Buyer ID</th><th>Message</th><th>Date</th><th>Status</th><th>Action</th></tr>
      <% for (PurchaseRequest req : requests) {
        Car car = carService.getCarById(req.getCarId());
        String carName = (car != null) ? car.getTitle() : "Unknown";
      %>
      <tr>
        <td><%= carName %></td>
        <td><%= req.getBuyerId() %></td>
        <td><%= req.getMessage() %></td>
        <td><%= req.getRequestDate() %></td>
        <td><%= req.getStatus() %></td>
        <td>
          <% if ("pending".equals(req.getStatus())) { %>
          <form action="updateRequestStatus" method="post" style="display:inline;">
            <input type="hidden" name="requestId" value="<%= req.getId() %>">
            <input type="hidden" name="action" value="accept">
            <button type="submit" class="btn">Accept</button>
          </form>
          <form action="updateRequestStatus" method="post" style="display:inline;">
            <input type="hidden" name="requestId" value="<%= req.getId() %>">
            <input type="hidden" name="action" value="reject">
            <button type="submit" class="btn btn-danger">Reject</button>
          </form>
          <% } else { %>
          <%= req.getStatus() %>
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