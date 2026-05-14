<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - AutoTrade Hub</title>
    <link rel="stylesheet" href="css/style.css">
    <script>
        function showFields() {
            var type = document.getElementById("userType").value;
            document.getElementById("buyerFields").style.display = (type === "buyer") ? "block" : "none";
            document.getElementById("sellerFields").style.display = (type === "seller") ? "block" : "none";
        }
    </script>
</head>
<body onload="showFields()">
<div class="navbar">
    <h1>🚗 AutoTrade Hub</h1>
    <div class="nav-links">
        <a href="index.jsp">Home</a>
        <a href="login.jsp">Login</a>
        <a href="register.jsp">Register</a>
    </div>
</div>
<div class="container">
    <div class="form-container" style="max-width:600px; margin:0 auto;">
        <h2>Register</h2>
        <% if(request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
        <form action="register" method="post">
            <div class="form-group"><label>Full Name</label><input type="text" name="name" required></div>
            <div class="form-group"><label>Email</label><input type="email" name="email" required></div>
            <div class="form-group"><label>Password</label><input type="password" name="password" required></div>
            <div class="form-group"><label>Phone</label><input type="text" name="phone" required></div>
            <div class="form-group">
                <label>I want to register as</label>
                <select id="userType" name="userType" onchange="showFields()">
                    <option value="buyer">Buyer</option>
                    <option value="seller">Seller</option>
                </select>
            </div>
            <div id="buyerFields">
                <div class="form-group"><label>Budget ($)</label><input type="number" name="budget"></div>
                <div class="form-group"><label>Preferred Brand</label><input type="text" name="preferredBrand"></div>
            </div>
            <div id="sellerFields" style="display:none">
                <div class="form-group"><label>Dealership Name</label><input type="text" name="dealershipName"></div>
                <div class="form-group"><label>Years in Business</label><input type="number" name="yearsInBusiness"></div>
            </div>
            <button type="submit" class="btn">Register</button>
        </form>
    </div>
</div>
<div class="footer"><p>&copy; 2025 AutoTrade Hub</p></div>
</body>
</html>