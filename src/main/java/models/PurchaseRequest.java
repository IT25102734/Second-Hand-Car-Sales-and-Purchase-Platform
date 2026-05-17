package models;

public class PurchaseRequest {
    private int id;
    private int carId;
    private int buyerId;
    private int sellerId;
    private String status;   // "pending", "accepted", "rejected"
    private String message;
    private String requestDate;

    public PurchaseRequest(int id, int carId, int buyerId, int sellerId,
                           String status, String message, String requestDate) {
        this.id = id;
        this.carId = carId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.status = status;
        this.message = message;
        this.requestDate = requestDate;
    }

    public PurchaseRequest() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }
    public int getBuyerId() { return buyerId; }
    public void setBuyerId(int buyerId) { this.buyerId = buyerId; }
    public int getSellerId() { return sellerId; }
    public void setSellerId(int sellerId) { this.sellerId = sellerId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getRequestDate() { return requestDate; }
    public void setRequestDate(String requestDate) { this.requestDate = requestDate; }

    // Convert to pipe-separated string
    public String toFileString() {
        return id + "|" + carId + "|" + buyerId + "|" + sellerId + "|" +
                status + "|" + message + "|" + requestDate;
    }

    // Factory method from pipe-separated line
    public static PurchaseRequest fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 7) return null;
        try {
            int id = Integer.parseInt(parts[0]);
            int carId = Integer.parseInt(parts[1]);
            int buyerId = Integer.parseInt(parts[2]);
            int sellerId = Integer.parseInt(parts[3]);
            String status = parts[4];
            String message = parts[5];
            String requestDate = parts[6];
            return new PurchaseRequest(id, carId, buyerId, sellerId, status, message, requestDate);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}