package models;

public class CertifiedCar extends Car {
    private int warrantyMonths;
    private String inspectionReport;

    public CertifiedCar(int id, String title, String description, double price, int year,
                        String make, String model, int mileage, String fuelType,
                        String transmission, int sellerId, String status,
                        int warrantyMonths, String inspectionReport) {
        super(id, title, description, price, year, make, model, mileage, fuelType,
                transmission, sellerId, status, "certified");
        this.warrantyMonths = warrantyMonths;
        this.inspectionReport = inspectionReport;
    }

    public int getWarrantyMonths() { return warrantyMonths; }
    public void setWarrantyMonths(int warrantyMonths) { this.warrantyMonths = warrantyMonths; }
    public String getInspectionReport() { return inspectionReport; }
    public void setInspectionReport(String inspectionReport) { this.inspectionReport = inspectionReport; }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + warrantyMonths + "|" + inspectionReport;
    }

    public static CertifiedCar fromFileString(String line) {
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
            String type = parts[12];
            int warrantyMonths = Integer.parseInt(parts[13]);
            String inspectionReport = parts[14];
            return new CertifiedCar(id, title, description, price, year, make, model, mileage,
                    fuelType, transmission, sellerId, status,
                    warrantyMonths, inspectionReport);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}