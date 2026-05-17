<%@ page import="models.User, models.SellerUser, utils.CarService, utils.RequestService, models.PurchaseRequest, models.Car, java.util.List" %>
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
    double totalEarnings = myListings.stream()
            .filter(c -> "sold".equals(c.getStatus()))
            .mapToDouble(Car::getPrice)
            .sum();

    List<PurchaseRequest> requests = requestService.getRequestsBySeller(seller.getId());
    long pendingRequests = requests.stream().filter(r -> "pending".equals(r.getStatus())).count();
    long acceptedRequests = requests.stream().filter(r -> "accepted".equals(r.getStatus())).count();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Seller Dashboard - AutoTrade Hub</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        /* Additional modern touches (optional, uses existing classes) */
        .welcome-section {
            background: linear-gradient(135deg, #1e2a3a, #0f1a24);
            color: white;
            padding: 1.5rem;
            border-radius: 28px;
            margin-bottom: 2rem;
            text-align: center;
        }
        .welcome-section h2 {
            margin: 0 0 0.5rem;
            font-size: 1.8rem;
        }
        .welcome-section p {
            margin: 0;
            opacity: 0.9;
        }
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
            gap: 1.5rem;
            margin: 2rem 0;
        }
        .stat-card {
            background: white;
            border-radius: 24px;
            padding: 1.2rem;
            text-align: center;
            box-shadow: 0 8px 20px rgba(0,0,0,0.05);
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 30px rgba(0,0,0,0.1);
        }
        .stat-icon {
            font-size: 2.5rem;
            margin-bottom: 0.5rem;
        }
        .stat-card h3 {
            font-size: 1rem;
            margin: 0.5rem 0;
            color: #1e2a3a;
            font-weight: 600;
        }
        .stat-card p {
            font-size: 1.8rem;
            font-weight: bold;
            color: #ffc107;
            margin: 0;
        }
        .action-buttons {
            display: flex;
            gap: 1rem;
            justify-content: center;
            flex-wrap: wrap;
            margin-top: 1rem;
        }
    </style>
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
        <a href="logout">Logout</a>
    </div>
</div>
<div class="container">
    <!-- Welcome Section -->
    <div class="welcome-section">
        <h2>Welcome, <%= seller.getName() %>!</h2>
        <p><%= seller.getDealershipName() %> · <%= seller.getYearsInBusiness() %> year(s) in business</p>
    </div>

    <!-- Statistics Cards -->
    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-icon">📦</div>
            <h3>Total Listings</h3>
            <p><%= totalListings %></p>
        </div>
        <div class="stat-card">
            <div class="stat-icon">✅</div>
            <h3>Available</h3>
            <p><%= availableCount %></p>
        </div>
        <div class="stat-card">
            <div class="stat-icon">💰</div>
            <h3>Sold</h3>
            <p><%= soldCount %></p>
        </div>
        <div class="stat-card">
            <div class="stat-icon">💵</div>
            <h3>Total Earnings</h3>
            <p>$<%= String.format("%,.2f", totalEarnings) %></p>
        </div>
        <div class="stat-card">
            <div class="stat-icon">⏳</div>
            <h3>Pending Requests</h3>
            <p><%= pendingRequests %></p>
        </div>
        <div class="stat-card">
            <div class="stat-icon">✅</div>
            <h3>Accepted Requests</h3>
            <p><%= acceptedRequests %></p>
        </div>
    </div>

    <!-- Action Buttons -->
    <div class="action-buttons">
        <a href="myListings" class="btn">📋 View My Listings</a>
        <a href="addCar.jsp" class="btn">➕ Add New Car</a>
        <a href="sellerRequests" class="btn">📨 View Requests</a>
    </div>
</div>
<div class="footer">
    <p>&copy; 2025 AutoTrade Hub</p>
</div>
</body>
</html>