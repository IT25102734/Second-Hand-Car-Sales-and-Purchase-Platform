package models;

public class UsedCar extends Car {
    private int previousOwners;
    private String serviceHistory;

    public UsedCar(int id, String title, String description, double price, int year,
                   String make, String model, int mileage, String fuelType,
                   String transmission, int sellerId, String status,
                   int previousOwners, String serviceHistory) {
        super(id, title, description, price, year, make, model, mileage, fuelType,
                transmission, sellerId, status, "used");
        this.previousOwners = previousOwners;
        this.serviceHistory = serviceHistory;
    }

    public int getPreviousOwners() { return previousOwners; }
    public void setPreviousOwners(int previousOwners) { this.previousOwners = previousOwners; }
    public String getServiceHistory() { return serviceHistory; }
    public void setServiceHistory(String serviceHistory) { this.serviceHistory = serviceHistory; }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + previousOwners + "|" + serviceHistory;
    }

    // Factory method to create UsedCar from line (15 fields total)
    public static UsedCar fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 15) return null;
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
            String type = parts[12];  // should be "used"
            int previousOwners = Integer.parseInt(parts[13]);
            String serviceHistory = parts[14];
            return new UsedCar(id, title, description, price, year, make, model, mileage,
                    fuelType, transmission, sellerId, status,
                    previousOwners, serviceHistory);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}