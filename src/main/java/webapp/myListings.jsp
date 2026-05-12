<%@ page import="models.Car, models.User, java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    User loggedUser = (User) session.getAttribute("loggedInUser");
    if (loggedUser == null || !"seller".equals(loggedUser.getUserType())) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<Car> cars = (List<Car>) request.getAttribute("cars");
    if (cars == null) {
        response.sendRedirect("myListings");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Listings - AutoTrade Hub</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
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
    <div class="card">
        <h2>My Car Listings</h2>
        <table>
            <tr><th>ID</th><th>Title</th><th>Price</th><th>Year</th><th>Status</th><th>Type</th><th>Actions</th></tr>
            <% if (cars.isEmpty()) { %>
            <tr><td colspan="7">You have no listings. <a href="addCar.jsp">Add one now</a></td></tr>
            <% } else { %>
            <% for (Car c : cars) { %>
            <tr>
                <td><%= c.getId() %></td>
                <td><%= c.getTitle() %></td>
                <td>$<%= c.getPrice() %></td>
                <td><%= c.getYear() %></td>
                <td><%= c.getStatus() %></td>
                <td><%= c.getType() %></td>
                <td>
                    <a href="editCar?id=<%= c.getId() %>" class="btn">Edit</a>
                    <form action="deleteCar" method="post" style="display:inline;" onsubmit="return confirm('Delete this listing?');">
                        <input type="hidden" name="id" value="<%= c.getId() %>">
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </form>
                </td>
            </tr>
            <% } %>
            <% } %>
        </table>
    </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>