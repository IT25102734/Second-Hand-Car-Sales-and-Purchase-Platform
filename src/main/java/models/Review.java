package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Review {
    private int id;
    private int carId;
    private int buyerId;
    private int rating;      // 1 to 5 stars
    private String comment;
    private String reviewDate;
    private String type;     // "public" or "verified"

    public Review(int id, int carId, int buyerId, int rating, String comment, String reviewDate, String type) {
        this.id = id;
        this.carId = carId;
        this.buyerId = buyerId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.type = type;
    }

    public Review() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }
    public int getBuyerId() { return buyerId; }
    public void setBuyerId(int buyerId) { this.buyerId = buyerId; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public String getReviewDate() { return reviewDate; }
    public void setReviewDate(String reviewDate) { this.reviewDate = reviewDate; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    // Convert to pipe-separated string
    public String toFileString() {
        return id + "|" + carId + "|" + buyerId + "|" + rating + "|" + comment + "|" + reviewDate + "|" + type;
    }

    // Factory method from pipe-separated line
    public static Review fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 7) return null;
        try {
            int id = Integer.parseInt(parts[0]);
            int carId = Integer.parseInt(parts[1]);
            int buyerId = Integer.parseInt(parts[2]);
            int rating = Integer.parseInt(parts[3]);
            String comment = parts[4];
            String reviewDate = parts[5];
            String type = parts[6];
            if ("public".equals(type)) {
                return new PublicReview(id, carId, buyerId, rating, comment, reviewDate);
            } else if ("verified".equals(type)) {
                return new VerifiedReview(id, carId, buyerId, rating, comment, reviewDate);
            } else {
                return new Review(id, carId, buyerId, rating, comment, reviewDate, type);
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }
}