package servlets;

import models.Brand;
import utils.BrandService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/brands")
public class ListBrandsServlet extends HttpServlet {
    private BrandService brandService = new BrandService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Brand> brands = brandService.getAllBrands();
        request.setAttribute("brands", brands);
        request.getRequestDispatcher("/brandList.jsp").forward(request, response);
    }
}