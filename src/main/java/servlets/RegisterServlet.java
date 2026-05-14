package servlets;

import models.*;
import utils.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String userType = request.getParameter("userType");

        User user;
        if ("buyer".equals(userType)) {
            int budget = Integer.parseInt(request.getParameter("budget"));
            String preferredBrand = request.getParameter("preferredBrand");
            user = new BuyerUser(0, name, email, password, phone, budget, preferredBrand);
        } else {
            String dealershipName = request.getParameter("dealershipName");
            int years = Integer.parseInt(request.getParameter("yearsInBusiness"));
            user = new SellerUser(0, name, email, password, phone, dealershipName, years);
        }

        boolean success = userService.registerUser(user);
        if (success) {
            response.sendRedirect("login.jsp?registered=true");
        } else {
            request.setAttribute("error", "Email already exists!");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}