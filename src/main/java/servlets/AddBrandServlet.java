package servlets;

import models.*;
import utils.BrandService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addBrand")
public class AddBrandServlet extends HttpServlet {
    private BrandService brandService = new BrandService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/addBrand.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String country = request.getParameter("country");
        String type = request.getParameter("type");

        Brand brand;
        if ("domestic".equals(type)) {
            int plants = Integer.parseInt(request.getParameter("plants"));
            brand = new DomesticBrand(0, name, country, plants);
        } else {
            double duty = Double.parseDouble(request.getParameter("duty"));
            brand = new ImportedBrand(0, name, country, duty);
        }

        brandService.addBrand(brand);
        response.sendRedirect("brands");
    }
}