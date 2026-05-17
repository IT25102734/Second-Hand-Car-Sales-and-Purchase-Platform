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
import java.util.List;

@WebServlet("/myRequests")
public class MyRequestsServlet extends HttpServlet {
    private RequestService requestService = new RequestService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User buyer = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (buyer == null || !"buyer".equals(buyer.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<PurchaseRequest> requests = requestService.getRequestsByBuyer(buyer.getId());
        request.setAttribute("requests", requests);
        request.getRequestDispatcher("/buyerRequests.jsp").forward(request, response);
    }
}