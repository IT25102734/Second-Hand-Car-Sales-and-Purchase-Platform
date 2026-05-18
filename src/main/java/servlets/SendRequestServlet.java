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

@WebServlet("/sendRequest")
public class SendRequestServlet extends HttpServlet {
    private RequestService requestService = new RequestService();
    private CarService carService = new CarService();

    // Handle GET request - show the form
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User buyer = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (buyer == null || !"buyer".equals(buyer.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String carIdParam = request.getParameter("carId");
        if (carIdParam == null) {
            response.sendRedirect("cars");
            return;
        }

        try {
            int carId = Integer.parseInt(carIdParam);
            Car car = carService.getCarById(carId);
            if (car == null || !"available".equals(car.getStatus())) {
                response.sendRedirect("cars?error=Car not available");
                return;
            }
            request.setAttribute("car", car);
            request.getRequestDispatcher("/sendRequest.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("cars");
        }
    }

    // Handle POST request - submit the form
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User buyer = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (buyer == null || !"buyer".equals(buyer.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }

        int carId = Integer.parseInt(request.getParameter("carId"));
        Car car = carService.getCarById(carId);
        if (car == null || !"available".equals(car.getStatus())) {
            response.sendRedirect("cars?error=Car not available");
            return;
        }

//        String message = request.getParameter("message");
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        PurchaseRequest purchaseRequest = new PurchaseRequest(
                0, carId, buyer.getId(), car.getSellerId(),
                "pending", message, date
        );

        requestService.addRequest(purchaseRequest);
        response.sendRedirect("myRequests?sent=true");
    }
}