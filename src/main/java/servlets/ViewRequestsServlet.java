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

@WebServlet("/sellerRequests")
public class ViewRequestsServlet extends HttpServlet {
    private RequestService requestService = new RequestService();
    private CarService carService = new CarService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User seller = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (seller == null || !"seller".equals(seller.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<PurchaseRequest> requests = requestService.getRequestsBySeller(seller.getId());
        request.setAttribute("requests", requests);
        request.getRequestDispatcher("/sellerRequests.jsp").forward(request, response);
    }
}