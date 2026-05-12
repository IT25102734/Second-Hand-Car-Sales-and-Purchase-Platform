package servlets;

import models.*;
import utils.CarService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/editCar")
public class EditCarServlet extends HttpServlet {
    private CarService carService = new CarService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (user == null || !"seller".equals(user.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }
        int carId = Integer.parseInt(request.getParameter("id"));
        Car car = carService.getCarById(carId);
        if (car == null || car.getSellerId() != user.getId()) {
            response.sendRedirect("myListings");
            return;
        }
        request.setAttribute("car", car);
        request.getRequestDispatcher("/editCar.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (user == null || !"seller".equals(user.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }
        int carId = Integer.parseInt(request.getParameter("id"));
        Car oldCar = carService.getCarById(carId);
        if (oldCar == null || oldCar.getSellerId() != user.getId()) {
            response.sendRedirect("myListings");
            return;
        }

        // Update common fields
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int year = Integer.parseInt(request.getParameter("year"));
        String make = request.getParameter("make");
        String model = request.getParameter("model");
        int mileage = Integer.parseInt(request.getParameter("mileage"));
        String fuelType = request.getParameter("fuelType");
        String transmission = request.getParameter("transmission");
        String status = request.getParameter("status");

        Car updatedCar;
        if ("used".equals(oldCar.getType())) {
            int previousOwners = Integer.parseInt(request.getParameter("previousOwners"));
            String serviceHistory = request.getParameter("serviceHistory");
            updatedCar = new UsedCar(carId, title, description, price, year, make, model, mileage,
                    fuelType, transmission, user.getId(), status,
                    previousOwners, serviceHistory);
        } else {
            int warrantyMonths = Integer.parseInt(request.getParameter("warrantyMonths"));
            String inspectionReport = request.getParameter("inspectionReport");
            updatedCar = new CertifiedCar(carId, title, description, price, year, make, model, mileage,
                    fuelType, transmission, user.getId(), status,
                    warrantyMonths, inspectionReport);
        }

        carService.updateCar(updatedCar);
        response.sendRedirect("myListings");
    }
}