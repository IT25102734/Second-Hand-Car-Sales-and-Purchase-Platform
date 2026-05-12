package servlets;

import models.Car;
import models.User;
import utils.CarService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/myListings")
public class MyListingsServlet extends HttpServlet {
    private CarService carService = new CarService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (user == null || !"seller".equals(user.getUserType())) {
            response.sendRedirect("login.jsp");
            return;
        }
        List<Car> cars = carService.getCarsBySeller(user.getId());
        request.setAttribute("cars", cars);
        request.getRequestDispatcher("/myListings.jsp").forward(request, response);
    }
}