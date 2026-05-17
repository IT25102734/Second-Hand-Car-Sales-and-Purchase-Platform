<%@ page import="models.Car, models.User, models.Review, models.PublicReview, models.VerifiedReview, utils.ReviewService, utils.RequestService" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  Car car = (Car) request.getAttribute("car");
  if (car == null) {
    response.sendRedirect("cars");
    return;
  }
  User loggedUser = (User) session.getAttribute("loggedInUser");
  boolean isBuyer = (loggedUser != null && "buyer".equals(loggedUser.getUserType()));
  boolean isAdmin = (loggedUser != null && loggedUser.isAdmin());

  ReviewService reviewService = new ReviewService();
  RequestService requestService = new RequestService();
  List<Review> reviews = reviewService.getReviewsByCar(car.getId());
  double avgRating = reviewService.getAverageRating(car.getId());

  boolean canReview = false;
  if (loggedUser != null && "buyer".equals(loggedUser.getUserType())) {
    canReview = requestService.getRequestsByBuyer(loggedUser.getId()).stream()
            .anyMatch(r -> r.getCarId() == car.getId() && "accepted".equals(r.getStatus()));
  }
%>
<!DOCTYPE html>
<html>
<head>
  <title><%= car.getTitle() %> - AutoTrade Hub</title>
  <link rel="stylesheet" href="css/style.css">
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
    <h2><%= car.getTitle() %></h2>
    <p><strong>Make/Model:</strong> <%= car.getMake() %> <%= car.getModel() %></p>
    <p><strong>Year:</strong> <%= car.getYear() %></p>
    <p><strong>Price:</strong> $<%= car.getPrice() %></p>
    <p><strong>Mileage:</strong> <%= car.getMileage() %> mi</p>
    <p><strong>Fuel Type:</strong> <%= car.getFuelType() %></p>
    <p><strong>Transmission:</strong> <%= car.getTransmission() %></p>
    <p><strong>Description:</strong> <%= car.getDescription() %></p>
    <p><strong>Status:</strong> <%= car.getStatus() %></p>
    <% if (isBuyer && "available".equals(car.getStatus())) { %>
    <a href="sendRequest?carId=<%= car.getId() %>" class="btn">Request to Buy</a>
    <% } %>
    <a href="cars" class="btn btn-outline">Back to Listings</a>

    <!-- Reviews Section -->
    <div style="margin-top: 2rem; border-top: 1px solid #eee; padding-top: 1.5rem;">
      <h3>Customer Reviews</h3>
      <div style="display: flex; align-items: center; gap: 1rem; margin-bottom: 1rem;">
        <div style="font-size: 2rem; font-weight: bold;"><%= String.format("%.1f", avgRating) %></div>
        <div style="color: #ffc107;">
          <% for (int i = 1; i <= 5; i++) { %>
          <% if (i <= Math.round(avgRating)) { %>★<% } else { %>☆<% } %>
          <% } %>
        </div>
        <div>(<%= reviews.size() %> review<% if (reviews.size() != 1) { %>s<% } %>)</div>
      </div>

      <% if (canReview) { %>
      <a href="addReview?carId=<%= car.getId() %>" class="btn" style="margin-bottom: 1rem;">Write a Review</a>
      <% } else if (loggedUser != null && "buyer".equals(loggedUser.getUserType())) { %>
      <p style="color: #888;">You can only review cars you have purchased.</p>
      <% } %>

      <% if (reviews.isEmpty()) { %>
      <p>No reviews yet. Be the first to review this car!</p>
      <% } else { %>
      <div style="max-height: 400px; overflow-y: auto;">
        <% for (Review r : reviews) {
          String reviewerName = "Anonymous";
          if (r instanceof PublicReview && ((PublicReview)r).isAnonymous()) {
            reviewerName = "Anonymous User";
          } else {
            reviewerName = "User " + r.getBuyerId();
          }
          String reviewBadge = "";
          if (r instanceof VerifiedReview) {
            reviewBadge = "<span style='background:#28a745; color:white; padding:2px 8px; border-radius:12px; font-size:0.7rem;'>Verified Purchase</span>";
          }
        %>
        <div style="border-bottom: 1px solid #eee; padding: 1rem 0;">
          <div style="display: flex; justify-content: space-between; align-items: center;">
            <div>
              <strong><%= reviewerName %></strong>
              <%= reviewBadge %>
            </div>
            <div>
              <% for (int i = 1; i <= 5; i++) { %>
              <% if (i <= r.getRating()) { %>★<% } else { %>☆<% } %>
              <% } %>
            </div>
          </div>
          <p><%= r.getComment() %></p>
          <small style="color: #888;"><%= r.getReviewDate() %></small>
          <% if (isAdmin) { %>
          <form action="deleteReview" method="post" style="display:inline; float:right;">
            <input type="hidden" name="reviewId" value="<%= r.getId() %>">
            <input type="hidden" name="carId" value="<%= car.getId() %>">
            <button type="submit" class="btn btn-danger" style="padding: 0.2rem 0.5rem; font-size: 0.8rem;">Delete</button>
          </form>
          <% } %>
        </div>
        <% } %>
      </div>
      <% } %>
    </div>
  </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>