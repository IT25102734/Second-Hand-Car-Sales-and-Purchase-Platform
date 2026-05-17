package servlets;

import models.User;
import utils.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/manageSellers")
public class ManageSellersServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User admin = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (admin == null || !admin.isAdmin()) {
            response.sendRedirect("login.jsp");
            return;
        }
        List<User> allUsers = userService.getAllUsers();
        List<User> sellers = allUsers.stream()
                .filter(u -> "seller".equals(u.getUserType()))
                .collect(Collectors.toList());
        request.setAttribute("sellers", sellers);
        request.getRequestDispatcher("/manageSellers.jsp").forward(request, response);
    }
}