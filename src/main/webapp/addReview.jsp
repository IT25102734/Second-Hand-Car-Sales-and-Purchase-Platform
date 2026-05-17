<%@ page import="models.Car, models.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Car car = (Car) request.getAttribute("car");
    if (car == null) response.sendRedirect("cars");
    User user = (User) session.getAttribute("loggedInUser");
    if (user == null || !"buyer".equals(user.getUserType())) response.sendRedirect("login.jsp");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Write a Review</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .rating { display: flex; flex-direction: row-reverse; justify-content: flex-end; gap: 0.5rem; }
        .rating input { display: none; }
        .rating label { font-size: 2rem; color: #ddd; cursor: pointer; }
        .rating input:checked ~ label, .rating label:hover, .rating label:hover ~ label { color: #ffc107; }
    </style>
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
        <h2>Write a Review for <%= car.getTitle() %></h2>
        <form action="addReview" method="post">
            <input type="hidden" name="carId" value="<%= car.getId() %>">
            <div class="form-group"><label>Rating</label><div class="rating"><input type="radio" name="rating" value="5" id="star5"><label for="star5">★</label><input type="radio" name="rating" value="4" id="star4"><label for="star4">★</label><input type="radio" name="rating" value="3" id="star3"><label for="star3">★</label><input type="radio" name="rating" value="2" id="star2"><label for="star2">★</label><input type="radio" name="rating" value="1" id="star1"><label for="star1">★</label></div></div>
            <div class="form-group"><label>Comment</label><textarea name="comment" rows="4" required></textarea></div>
            <div class="form-group"><label><input type="checkbox" name="anonymous"> Post anonymously</label></div>
            <button type="submit" class="btn">Submit Review</button>
            <a href="carDetails?id=<%= car.getId() %>" class="btn btn-outline">Cancel</a>
        </form>
    </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>