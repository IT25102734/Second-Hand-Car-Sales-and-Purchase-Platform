package utils;

import models.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarService {
    private static final String CAR_FILE = "cars.txt";

    // Helper: parse line into correct subclass
    private Car parseCarFromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 13) return null;
        String type = parts[12];
        if ("used".equals(type)) {
            return UsedCar.fromFileString(line);
        } else if ("certified".equals(type)) {
            return CertifiedCar.fromFileString(line);
        } else {
            return Car.fromFileString(line);
        }
    }

    // CREATE
    public boolean addCar(Car car) throws IOException {
        List<String> lines = FileUtil.readFromFile(CAR_FILE);
        int newId = lines.size() + 1;
        car.setId(newId);
        FileUtil.writeToFile(CAR_FILE, car.toFileString(), true);
        return true;
    }

    // READ - all cars
    public List<Car> getAllCars() throws IOException {
        List<Car> cars = new ArrayList<>();
        List<String> lines = FileUtil.readFromFile(CAR_FILE);
        for (String line : lines) {
            Car car = parseCarFromLine(line);
            if (car != null) cars.add(car);
        }
        return cars;
    }

    // READ - cars by seller ID
    public List<Car> getCarsBySeller(int sellerId) throws IOException {
        return getAllCars().stream()
                .filter(c -> c.getSellerId() == sellerId)
                .collect(Collectors.toList());
    }

    // READ - available cars
    public List<Car> getAvailableCars() throws IOException {
        return getAllCars().stream()
                .filter(c -> "available".equalsIgnoreCase(c.getStatus()))
                .collect(Collectors.toList());
    }

    // READ - car by ID
    public Car getCarById(int id) throws IOException {
        return getAllCars().stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // UPDATE
    public boolean updateCar(Car updatedCar) throws IOException {
        List<String> lines = FileUtil.readFromFile(CAR_FILE);
        List<String> newLines = new ArrayList<>();
        boolean updated = false;
        for (String line : lines) {
            Car existing = parseCarFromLine(line);
            if (existing != null && existing.getId() == updatedCar.getId()) {
                newLines.add(updatedCar.toFileString());
                updated = true;
            } else {
                newLines.add(line);
            }
        }
        if (updated) {
            FileUtil.deleteFile(CAR_FILE);
            for (String line : newLines) {
                FileUtil.writeToFile(CAR_FILE, line, true);
            }
        }
        return updated;
    }

    // DELETE
    public boolean deleteCar(int carId) throws IOException {
        List<String> lines = FileUtil.readFromFile(CAR_FILE);
        List<String> newLines = new ArrayList<>();
        boolean deleted = false;
        for (String line : lines) {
            Car car = parseCarFromLine(line);
            if (car != null && car.getId() == carId) {
                deleted = true;
            } else {
                newLines.add(line);
            }
        }
        if (deleted) {
            FileUtil.deleteFile(CAR_FILE);
            for (String line : newLines) {
                FileUtil.writeToFile(CAR_FILE, line, true);
            }
        }
        return deleted;
    }
}