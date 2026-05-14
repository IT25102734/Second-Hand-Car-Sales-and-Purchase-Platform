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

@WebServlet("/editProfile")
public class EditProfileServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("/editProfile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User oldUser = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (oldUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Create updated user object using the correct constructor
        User updatedUser = new User(
                oldUser.getId(),
                request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("password"),
                request.getParameter("phone"),
                oldUser.getUserType(),   // userType cannot be changed
                oldUser.isAdmin()        // preserve admin status
        );

        boolean success = userService.updateUser(updatedUser);
        if (success) {
            session.setAttribute("loggedInUser", updatedUser);
            response.sendRedirect("profile?updated=true");
        } else {
            request.setAttribute("error", "Update failed");
            request.getRequestDispatcher("/editProfile.jsp").forward(request, response);
        }
    }
}