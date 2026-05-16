package servlets;

import models.*;
import utils.BrandService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editBrand")
public class EditBrandServlet extends HttpServlet {
    private BrandService brandService = new BrandService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Brand brand = brandService.getBrandById(id);
        if (brand == null) {
            response.sendRedirect("brands");
            return;
        }
        request.setAttribute("brand", brand);
        request.getRequestDispatcher("/editBrand.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String country = request.getParameter("country");
        String type = request.getParameter("type");

        Brand brand;
        if ("domestic".equals(type)) {
            int plants = Integer.parseInt(request.getParameter("plants"));
            brand = new DomesticBrand(id, name, country, plants);
        } else {
            double duty = Double.parseDouble(request.getParameter("duty"));
            brand = new ImportedBrand(id, name, country, duty);
        }

        brandService.updateBrand(brand);
        response.sendRedirect("brands");
    }
}