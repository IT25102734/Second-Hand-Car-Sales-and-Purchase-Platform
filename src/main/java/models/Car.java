package models;

public class Car {
    private int id;
    private String title;
    private String description;
    private double price;
    private int year;
    private String make;
    private String model;
    private int mileage;
    private String fuelType;      // petrol, diesel, electric
    private String transmission;  // manual, automatic
    private int sellerId;
    private String status;        // "available" or "sold"
    private String type;          // "used" or "certified"

    public Car(int id, String title, String description, double price, int year,
               String make, String model, int mileage, String fuelType,
               String transmission, int sellerId, String status, String type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.year = year;
        this.make = make;
        this.model = model;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.sellerId = sellerId;
        this.status = status;
        this.type = type;
    }

    public Car() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getMileage() { return mileage; }
    public void setMileage(int mileage) { this.mileage = mileage; }
    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public String getTransmission() { return transmission; }
    public void setTransmission(String transmission) { this.transmission = transmission; }
    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    // Convert to pipe-separated string
    public String toFileString() {
        return id + "|" + title + "|" + description + "|" + price + "|" + year + "|" +
                make + "|" + model + "|" + mileage + "|" + fuelType + "|" + transmission + "|" +
                sellerId + "|" + status + "|" + type;
    }

    // Create Car from pipe-separated line (basic fields only)
    public static Car fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 13) return null;
        try {
            int id = Integer.parseInt(parts[0]);
            String title = parts[1];
            String description = parts[2];
            double price = Double.parseDouble(parts[3]);
            int year = Integer.parseInt(parts[4]);
            String make = parts[5];
            String model = parts[6];
            int mileage = Integer.parseInt(parts[7]);
            String fuelType = parts[8];
            String transmission = parts[9];
            int sellerId = Integer.parseInt(parts[10]);
            String status = parts[11];
            String type = parts[12];
            return new Car(id, title, description, price, year, make, model, mileage,
                    fuelType, transmission, sellerId, status, type);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}