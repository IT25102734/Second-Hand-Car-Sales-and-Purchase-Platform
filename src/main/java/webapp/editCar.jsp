<%@ page import="models.Car, models.UsedCar, models.CertifiedCar, models.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User loggedUser = (User) session.getAttribute("loggedInUser");
    if (loggedUser == null || !"seller".equals(loggedUser.getUserType())) {
        response.sendRedirect("login.jsp");
        return;
    }
    Car car = (Car) request.getAttribute("car");
    if (car == null) {
        response.sendRedirect("myListings");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Car - AutoTrade Hub</title>
    <link rel="stylesheet" href="css/style.css">
    <script>
        function showCarTypeFields() {
            var type = "<%= car.getType() %>";
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
        <h2>Edit Car Listing</h2>
        <form action="editCar" method="post">
            <input type="hidden" name="id" value="<%= car.getId() %>">
            <div class="form-group"><label>Title</label><input type="text" name="title" value="<%= car.getTitle() %>" required></div>
            <div class="form-group"><label>Description</label><textarea name="description" rows="3"><%= car.getDescription() %></textarea></div>
            <div class="form-group"><label>Price ($)</label><input type="number" step="0.01" name="price" value="<%= car.getPrice() %>" required></div>
            <div class="form-group"><label>Year</label><input type="number" name="year" value="<%= car.getYear() %>" required></div>
            <div class="form-group"><label>Make</label><input type="text" name="make" value="<%= car.getMake() %>" required></div>
            <div class="form-group"><label>Model</label><input type="text" name="model" value="<%= car.getModel() %>" required></div>
            <div class="form-group"><label>Mileage</label><input type="number" name="mileage" value="<%= car.getMileage() %>" required></div>
            <div class="form-group">
                <label>Fuel Type</label>
                <select name="fuelType">
                    <option <%= "Petrol".equals(car.getFuelType()) ? "selected" : "" %>>Petrol</option>
                    <option <%= "Diesel".equals(car.getFuelType()) ? "selected" : "" %>>Diesel</option>
                    <option <%= "Electric".equals(car.getFuelType()) ? "selected" : "" %>>Electric</option>
                    <option <%= "Hybrid".equals(car.getFuelType()) ? "selected" : "" %>>Hybrid</option>
                </select>
            </div>
            <div class="form-group">
                <label>Transmission</label>
                <select name="transmission">
                    <option <%= "Manual".equals(car.getTransmission()) ? "selected" : "" %>>Manual</option>
                    <option <%= "Automatic".equals(car.getTransmission()) ? "selected" : "" %>>Automatic</option>
                </select>
            </div>
            <div class="form-group">
                <label>Status</label>
                <select name="status">
                    <option <%= "available".equals(car.getStatus()) ? "selected" : "" %>>available</option>
                    <option <%= "sold".equals(car.getStatus()) ? "selected" : "" %>>sold</option>
                </select>
            </div>
            <div id="usedFields" style="display:none">
                <div class="form-group"><label>Previous Owners</label><input type="number" name="previousOwners" value="<%= (car.getType().equals("used")) ? ((UsedCar)car).getPreviousOwners() : "" %>"></div>
                <div class="form-group">
                    <label>Service History</label>
                    <select name="serviceHistory">
                        <option <%= (car.getType().equals("used") && "Available".equals(((UsedCar)car).getServiceHistory())) ? "selected" : "" %>>Available</option>
                        <option <%= (car.getType().equals("used") && "Not Available".equals(((UsedCar)car).getServiceHistory())) ? "selected" : "" %>>Not Available</option>
                    </select>
                </div>
            </div>
            <div id="certifiedFields" style="display:none">
                <div class="form-group"><label>Warranty (months)</label><input type="number" name="warrantyMonths" value="<%= (car.getType().equals("certified")) ? ((CertifiedCar)car).getWarrantyMonths() : "" %>"></div>
                <div class="form-group">
                    <label>Inspection Report</label>
                    <select name="inspectionReport">
                        <option <%= (car.getType().equals("certified") && "Available".equals(((CertifiedCar)car).getInspectionReport())) ? "selected" : "" %>>Available</option>
                        <option <%= (car.getType().equals("certified") && "Not Available".equals(((CertifiedCar)car).getInspectionReport())) ? "selected" : "" %>>Not Available</option>
                    </select>
                </div>
            </div>
            <button type="submit" class="btn">Save Changes</button>
        </form>
    </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>