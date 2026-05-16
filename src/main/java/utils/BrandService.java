package utils;

import models.Brand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BrandService {
    private static final String BRAND_FILE = "brands.txt";

    // CREATE
    public boolean addBrand(Brand brand) throws IOException {
        List<String> lines = FileUtil.readFromFile(BRAND_FILE);
        int newId = lines.size() + 1;
        brand.setId(newId);
        FileUtil.writeToFile(BRAND_FILE, brand.toFileString(), true);
        return true;
    }

    // READ all brands
    public List<Brand> getAllBrands() throws IOException {
        List<Brand> brands = new ArrayList<>();
        List<String> lines = FileUtil.readFromFile(BRAND_FILE);
        for (String line : lines) {
            Brand brand = Brand.fromFileString(line);
            if (brand != null) brands.add(brand);
        }
        return brands;
    }

    // READ brand by ID
    public Brand getBrandById(int id) throws IOException {
        return getAllBrands().stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // READ brand by name
    public Brand getBrandByName(String name) throws IOException {
        return getAllBrands().stream()
                .filter(b -> b.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    // UPDATE
    public boolean updateBrand(Brand updatedBrand) throws IOException {
        List<String> lines = FileUtil.readFromFile(BRAND_FILE);
        List<String> newLines = new ArrayList<>();
        boolean updated = false;
        for (String line : lines) {
            Brand existing = Brand.fromFileString(line);
            if (existing != null && existing.getId() == updatedBrand.getId()) {
                newLines.add(updatedBrand.toFileString());
                updated = true;
            } else {
                newLines.add(line);
            }
        }
        if (updated) {
            FileUtil.deleteFile(BRAND_FILE);
            for (String line : newLines) {
                FileUtil.writeToFile(BRAND_FILE, line, true);
            }
        }
        return updated;
    }

    // DELETE
    public boolean deleteBrand(int brandId) throws IOException {
        List<String> lines = FileUtil.readFromFile(BRAND_FILE);
        List<String> newLines = new ArrayList<>();
        boolean deleted = false;
        for (String line : lines) {
            Brand brand = Brand.fromFileString(line);
            if (brand != null && brand.getId() == brandId) {
                deleted = true;
            } else {
                newLines.add(line);
            }
        }
        if (deleted) {
            FileUtil.deleteFile(BRAND_FILE);
            for (String line : newLines) {
                FileUtil.writeToFile(BRAND_FILE, line, true);
            }
        }
        return deleted;
    }
}