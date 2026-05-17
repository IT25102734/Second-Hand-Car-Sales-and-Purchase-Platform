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

@WebServlet("/editSeller")
public class EditSellerServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User admin = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (admin == null || !admin.isAdmin()) {
            response.sendRedirect("login.jsp");
            return;
        }
        int id = Integer.parseInt(request.getParameter("id"));
        User seller = userService.getUserById(id);
        if (seller == null || !"seller".equals(seller.getUserType())) {
            response.sendRedirect("manageSellers");
            return;
        }
        request.setAttribute("seller", seller);
        request.getRequestDispatcher("/editSeller.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User admin = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (admin == null || !admin.isAdmin()) {
            response.sendRedirect("login.jsp");
            return;
        }
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        // Note: We don't allow changing userType or isAdmin via this form for security.
        User seller = userService.getUserById(id);
        if (seller != null && "seller".equals(seller.getUserType())) {
            seller.setName(name);
            seller.setEmail(email);
            seller.setPhone(phone);
            userService.updateUser(seller);
        }
        response.sendRedirect("manageSellers");
    }
}