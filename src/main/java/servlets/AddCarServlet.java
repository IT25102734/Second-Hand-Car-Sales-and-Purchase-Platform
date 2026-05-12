package servlets;

import models.Car;
import models.User;
import models.*;
import utils.CarService;
import utils.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/addCar")
public class AddCarServlet extends HttpServlet {
    private CarService carService = new CarService();
    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (user == null || !"seller".equals(user.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }
        request.getRequestDispatcher("/addCar.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (user == null || !"seller".equals(user.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Common fields
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int year = Integer.parseInt(request.getParameter("year"));
        String make = request.getParameter("make");
        String model = request.getParameter("model");
        int mileage = Integer.parseInt(request.getParameter("mileage"));
        String fuelType = request.getParameter("fuelType");
        String transmission = request.getParameter("transmission");
        String carType = request.getParameter("carType"); // "used" or "certified"

        Car car;
        if ("used".equals(carType)) {
            int previousOwners = Integer.parseInt(request.getParameter("previousOwners"));
            String serviceHistory = request.getParameter("serviceHistory");
            car = new UsedCar(0, title, description, price, year, make, model, mileage,
                    fuelType, transmission, user.getId(), "available",
                    previousOwners, serviceHistory);
        } else {
            int warrantyMonths = Integer.parseInt(request.getParameter("warrantyMonths"));
            String inspectionReport = request.getParameter("inspectionReport");
            car = new CertifiedCar(0, title, description, price, year, make, model, mileage,
                    fuelType, transmission, user.getId(), "available",
                    warrantyMonths, inspectionReport);
        }

        carService.addCar(car);
        response.sendRedirect("myListings");
    }
}