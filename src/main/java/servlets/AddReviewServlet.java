package servlets;

import models.*;
import utils.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/addReview")
public class AddReviewServlet extends HttpServlet {
    private ReviewService reviewService = new ReviewService();
    private CarService carService = new CarService();
    private RequestService requestService = new RequestService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User buyer = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (buyer == null || !"buyer".equals(buyer.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }
        int carId = Integer.parseInt(request.getParameter("carId"));
        Car car = carService.getCarById(carId);
        if (car == null) {
            response.sendRedirect("cars");
            return;
        }
        request.setAttribute("car", car);
        request.getRequestDispatcher("/addReview.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User buyer = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (buyer == null || !"buyer".equals(buyer.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }

        int carId = Integer.parseInt(request.getParameter("carId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String comment = request.getParameter("comment");
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Check if this buyer has purchased the car (accepted request)
        boolean hasPurchased = false;
        for (PurchaseRequest req : requestService.getRequestsByBuyer(buyer.getId())) {
            if (req.getCarId() == carId && "accepted".equals(req.getStatus())) {
                hasPurchased = true;
                break;
            }
        }

        Review review;
        if (hasPurchased) {
            // Verified review
            review = new VerifiedReview(0, carId, buyer.getId(), rating, comment, date);
        } else {
            // Public review; check if anonymous
            boolean anonymous = "on".equals(request.getParameter("anonymous"));
            review = new PublicReview(0, carId, buyer.getId(), rating, comment, date, anonymous);
        }

        reviewService.addReview(review);
        response.sendRedirect("carDetails?id=" + carId + "&reviewAdded=true");
    }
}