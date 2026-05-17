package servlets;

import models.User;
import utils.ReviewService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteReview")
public class DeleteReviewServlet extends HttpServlet {
    private ReviewService reviewService = new ReviewService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User admin = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (admin == null || !admin.isAdmin()) {
            response.sendRedirect("login.jsp");
            return;
        }
        int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        int carId = Integer.parseInt(request.getParameter("carId"));
        reviewService.deleteReview(reviewId);
        response.sendRedirect("carDetails?id=" + carId + "&reviewDeleted=true");
    }
}