<%@ page import="models.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User user = (User) session.getAttribute("loggedInUser");
    if (user == null || !"seller".equals(user.getUserType())) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Car Listing - AutoTrade Hub</title>
    <link rel="stylesheet" href="css/style.css">
    <script>
        function showCarTypeFields() {
            var type = document.getElementById("carType").value;
            document.getElementById("usedFields").style.display = (type === "used") ? "block" : "none";
            document.getElementById("certifiedFields").style.display = (type === "certified") ? "block" : "none";
        }
    </script>
</head>
<body onload="showCarTypeFields()">
<div class="navbar">
    <h1>🚗 AutoTrade Hub</h1>
    <div class="nav-links">
        <a href="index.jsp">Home</a>
        <a href="sellerDashboard.jsp">Dashboard</a>
        <a href="myListings">My Listings</a>
        <a href="addCar.jsp">Add Car</a>
        <a href="sellerRequests">Requests</a>
        <a href="profile.jsp">Profile</a>
        <a href="logout">Logout</a>
    </div>
</div>
<div class="container">
    <div class="form-container" style="max-width: 700px; margin: 0 auto;">
        <h2>Add New Car Listing</h2>
        <form action="addCar" method="post">
            <div class="form-group"><label>Title</label><input type="text" name="title" required></div>
            <div class="form-group"><label>Description</label><textarea name="description" rows="3"></textarea></div>
            <div class="form-group"><label>Price ($)</label><input type="number" step="0.01" name="price" required></div>
            <div class="form-group"><label>Year</label><input type="number" name="year" required></div>
            <div class="form-group"><label>Make</label><input type="text" name="make" required></div>
            <div class="form-group"><label>Model</label><input type="text" name="model" required></div>
            <div class="form-group"><label>Mileage</label><input type="number" name="mileage" required></div>
            <div class="form-group">
                <label>Fuel Type</label>
                <select name="fuelType">
                    <option>Petrol</option><option>Diesel</option><option>Electric</option><option>Hybrid</option>
                </select>
            </div>
            <div class="form-group">
                <label>Transmission</label>
                <select name="transmission">
                    <option>Manual</option><option>Automatic</option>
                </select>
            </div>
            <div class="form-group">
                <label>Car Type</label>
                <select id="carType" name="carType" onchange="showCarTypeFields()">
                    <option value="used">Used Car</option>
                    <option value="certified">Certified Car</option>
                </select>
            </div>
            <div id="usedFields">
                <div class="form-group"><label>Previous Owners</label><input type="number" name="previousOwners"></div>
                <div class="form-group">
                    <label>Service History</label>
                    <select name="serviceHistory">
                        <option>Available</option><option>Not Available</option>
                    </select>
                </div>
            </div>
            <div id="certifiedFields" style="display:none">
                <div class="form-group"><label>Warranty (months)</label><input type="number" name="warrantyMonths"></div>
                <div class="form-group">
                    <label>Inspection Report</label>
                    <select name="inspectionReport">
                        <option>Available</option><option>Not Available</option>
                    </select>
                </div>
            </div>
            <button type="submit" class="btn">Add Car</button>
        </form>
    </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>