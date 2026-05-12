package servlets;

import models.Car;                    // <-- ADD THIS LINE
import utils.CarService;
import models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteCar")
public class DeleteCarServlet extends HttpServlet {
    private CarService carService = new CarService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (user == null || !"seller".equals(user.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }
        int carId = Integer.parseInt(request.getParameter("id"));
        Car car = carService.getCarById(carId);
        if (car != null && car.getSellerId() == user.getId()) {
            carService.deleteCar(carId);
        }
        response.sendRedirect("myListings");
    }
}