<%@ page import="models.User, models.SellerUser, utils.CarService, utils.RequestService, models.PurchaseRequest, models.Car" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User user = (User) session.getAttribute("loggedInUser");
    if (user == null || !"seller".equals(user.getUserType())) {
        response.sendRedirect("login.jsp");
        return;
    }
    SellerUser seller = (SellerUser) user;
    CarService carService = new CarService();
    RequestService requestService = new RequestService();

    List<Car> myListings = carService.getCarsBySeller(seller.getId());
    long totalListings = myListings.size();
    long soldCount = myListings.stream().filter(c -> "sold".equals(c.getStatus())).count();
    long availableCount = totalListings - soldCount;

    List<PurchaseRequest> requests = requestService.getRequestsBySeller(seller.getId());
    long pendingRequests = requests.stream().filter(r -> "pending".equals(r.getStatus())).count();
    long acceptedRequests = requests.stream().filter(r -> "accepted".equals(r.getStatus())).count();

    // Total earnings = sum of prices of sold cars
    double totalEarnings = myListings.stream()
            .filter(c -> "sold".equals(c.getStatus()))
            .mapToDouble(Car::getPrice)
            .sum();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Seller Dashboard - AutoTrade Hub</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="navbar">
    <h1>🚗 AutoTrade Hub</h1>
    <div class="nav-links">
        <a href="index.jsp">Home</a>
        <a href="myListings">My Listings</a>
        <a href="addCar.jsp">Add Car</a>
        <a href="sellerRequests">Requests</a>
        <a href="profile.jsp">Profile</a>
        <% if (user.isAdmin()) { %>
        <a href="manageSellers">Manage Sellers</a>
        <% } %>
        <a href="logout">Logout</a>
    </div>
</div>
<div class="container">
    <div class="card">
        <h2>Welcome, <%= seller.getName() %>!</h2>
        <p><strong>Dealership:</strong> <%= seller.getDealershipName() %></p>
        <p><strong>Years in Business:</strong> <%= seller.getYearsInBusiness() %></p>
        <div style="margin-top: 2rem; display: flex; flex-wrap: wrap; gap: 1.5rem;">
            <div class="stat-card">
                <h3>📦 Total Listings</h3>
                <p><%= totalListings %></p>
            </div>
            <div class="stat-card">
                <h3>✅ Available</h3>
                <p><%= availableCount %></p>
            </div>
            <div class="stat-card">
                <h3>💰 Sold</h3>
                <p><%= soldCount %></p>
            </div>
            <div class="stat-card">
                <h3>💵 Total Earnings</h3>
                <p>$<%= String.format("%.2f", totalEarnings) %></p>
            </div>
            <div class="stat-card">
                <h3>⏳ Pending Requests</h3>
                <p><%= pendingRequests %></p>
            </div>
            <div class="stat-card">
                <h3>✅ Accepted Requests</h3>
                <p><%= acceptedRequests %></p>
            </div>
        </div>
        <div style="margin-top: 2rem;">
            <a href="myListings" class="btn">View My Listings</a>
            <a href="addCar.jsp" class="btn">Add New Car</a>
            <a href="sellerRequests" class="btn">View Requests</a>
        </div>
    </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>