<%@ page import="models.Car, models.User, models.PurchaseRequest, models.Review, models.PublicReview, models.VerifiedReview, utils.RequestService, utils.ReviewService, java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Car car = (Car) request.getAttribute("car");
    if (car == null) response.sendRedirect("cars");
    User loggedUser = (User) session.getAttribute("loggedInUser");
    boolean isBuyer = (loggedUser != null && "buyer".equals(loggedUser.getUserType()));
    boolean isAdmin = (loggedUser != null && loggedUser.isAdmin());

    ReviewService reviewService = new ReviewService();
    RequestService requestService = new RequestService();
    List<Review> reviews = reviewService.getReviewsByCar(car.getId());
    double avg = reviewService.getAverageRating(car.getId());

    java.util.Set<Integer> purchasedCarIds = new java.util.HashSet<>();
    if (loggedUser != null && "buyer".equals(loggedUser.getUserType())) {
        for (PurchaseRequest r : requestService.getRequestsByBuyer(loggedUser.getId())) {
            if ("accepted".equals(r.getStatus())) purchasedCarIds.add(r.getCarId());
        }
    }
    boolean hasPurchased = purchasedCarIds.contains(car.getId());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= car.getTitle() %></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="navbar">... same navbar as other pages ...</div>
<div class="container">
    <div class="card">
        <h2><%= car.getTitle() %></h2>
        <p><strong>Make/Model:</strong> <%= car.getMake() %> <%= car.getModel() %></p>
        <p><strong>Year:</strong> <%= car.getYear() %></p>
        <p><strong>Price:</strong> $<%= String.format("%.2f", car.getPrice()) %></p>
        <p><strong>Mileage:</strong> <%= car.getMileage() %> mi</p>
        <p><strong>Fuel Type:</strong> <%= car.getFuelType() %></p>
        <p><strong>Transmission:</strong> <%= car.getTransmission() %></p>
        <p><strong>Description:</strong> <%= car.getDescription() %></p>
        <p><strong>Status:</strong> <%= car.getStatus() %></p>
        <% if (isBuyer && "available".equals(car.getStatus())) { %>
        <a href="sendRequest?carId=<%= car.getId() %>" class="btn">Request to Buy</a>
        <% } %>
        <a href="cars" class="btn btn-outline">Back to Listings</a>

        <div style="margin-top:2rem; border-top:1px solid #eee; padding-top:1.5rem;">
            <h3>Customer Reviews</h3>
            <div>Average rating: <%= String.format("%.1f", avg) %> / 5</div>
            <% if (isBuyer) { %>
            <a href="addReview?carId=<%= car.getId() %>" class="btn">Write a Review</a>
            <% } %>
            <% if (reviews.isEmpty()) { %>
            <p>No reviews yet.</p>
            <% } else { %>
            <div>
                <% for (Review r : reviews) {
                    String name = (r instanceof PublicReview && ((PublicReview)r).isAnonymous()) ? "Anonymous" : "User "+r.getBuyerId();
                    String badge = (r instanceof VerifiedReview) ? "<span style='background:#28a745;color:white;padding:2px 8px;border-radius:12px;font-size:0.7rem;margin-left:8px;'>Verified Purchase</span>" : "";
                %>
                <div style="border-bottom:1px solid #eee; padding:1rem 0;">
                    <div><strong><%= name %></strong> <%= badge %></div>
                    <div>Rating: <%= r.getRating() %> stars</div>
                    <p><%= r.getComment() %></p>
                    <small><%= r.getReviewDate() %></small>
                    <% if (isAdmin) { %>
                    <form action="deleteReview" method="post" style="display:inline; float:right;">
                        <input type="hidden" name="reviewId" value="<%= r.getId() %>">
                        <input type="hidden" name="carId" value="<%= car.getId() %>">
                        <button type="submit" class="btn btn-danger" style="padding:0.2rem 0.5rem; font-size:0.8rem;">Delete</button>
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