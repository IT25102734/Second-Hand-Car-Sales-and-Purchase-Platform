package servlets;

import utils.UserService;
import models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteSeller")
public class DeleteSellerServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User admin = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (admin == null || !admin.isAdmin()) {
            response.sendRedirect("login.jsp");
            return;
        }
        int id = Integer.parseInt(request.getParameter("id"));
        User seller = userService.getUserById(id);
        if (seller != null && "seller".equals(seller.getUserType())) {
            // Optional: delete all cars owned by this seller
            // For now, just delete the seller account.
            userService.deleteUser(id);
        }
        response.sendRedirect("manageSellers");
    }
}