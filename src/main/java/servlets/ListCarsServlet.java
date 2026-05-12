package servlets;

import models.Car;
import models.Brand;
import utils.CarService;
import utils.BrandService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/cars")
public class ListCarsServlet extends HttpServlet {
    private CarService carService = new CarService();
    private BrandService brandService = new BrandService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get filter parameters
        String searchMake = request.getParameter("make");
        String searchModel = request.getParameter("model");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        String brandIdParam = request.getParameter("brandId");

        // If a brand is selected, override the make filter with the brand's name
        if (brandIdParam != null && !brandIdParam.isEmpty()) {
            try {
                int brandId = Integer.parseInt(brandIdParam);
                Brand brand = brandService.getBrandById(brandId);
                if (brand != null) {
                    searchMake = brand.getName();
                }
            } catch (NumberFormatException e) {
                // ignore invalid brandId
            }
        }

        // Start with all available cars
        List<Car> cars = carService.getAvailableCars();

        // Apply filters using stream (compatible with older Java)
        if (searchMake != null && !searchMake.isEmpty()) {
            final String finalMake = searchMake;
            cars = cars.stream()
                    .filter(c -> c.getMake().equalsIgnoreCase(finalMake))
                    .collect(Collectors.toList());
        }
        if (searchModel != null && !searchModel.isEmpty()) {
            final String finalModel = searchModel;
            cars = cars.stream()
                    .filter(c -> c.getModel().equalsIgnoreCase(finalModel))
                    .collect(Collectors.toList());
        }
        if (minPrice != null && !minPrice.isEmpty()) {
            double min = Double.parseDouble(minPrice);
            cars = cars.stream()
                    .filter(c -> c.getPrice() >= min)
                    .collect(Collectors.toList());
        }
        if (maxPrice != null && !maxPrice.isEmpty()) {
            double max = Double.parseDouble(maxPrice);
            cars = cars.stream()
                    .filter(c -> c.getPrice() <= max)
                    .collect(Collectors.toList());
        }

        // Also pass the list of all brands to the JSP for the dropdown
        List<Brand> allBrands = brandService.getAllBrands();
        request.setAttribute("brands", allBrands);
        request.setAttribute("cars", cars);
        request.getRequestDispatcher("/carList.jsp").forward(request, response);
    }
}