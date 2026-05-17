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

@WebServlet("/updateRequestStatus")
public class UpdateRequestStatusServlet extends HttpServlet {
    private RequestService requestService = new RequestService();
    private CarService carService = new CarService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User seller = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (seller == null || !"seller".equals(seller.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }

        int requestId = Integer.parseInt(request.getParameter("requestId"));
        String action = request.getParameter("action"); // "accept" or "reject"
        String newStatus = "accept".equals(action) ? "accepted" : "rejected";

        PurchaseRequest req = requestService.getRequestById(requestId);
        if (req != null && req.getSellerId() == seller.getId()) {
            requestService.updateRequestStatus(requestId, newStatus);
            if ("accept".equals(action)) {
                // Mark the car as sold
                Car car = carService.getCarById(req.getCarId());
                if (car != null) {
                    car.setStatus("sold");
                    carService.updateCar(car);
                }
            }
        }
        response.sendRedirect("sellerRequests");
    }
}