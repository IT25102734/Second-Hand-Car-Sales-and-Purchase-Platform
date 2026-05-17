package utils;

import models.Review;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewService {
    private static final String REVIEW_FILE = "reviews.txt";

    // CREATE
    public boolean addReview(Review review) throws IOException {
        List<String> lines = FileUtil.readFromFile(REVIEW_FILE);
        int newId = lines.size() + 1;
        review.setId(newId);
        FileUtil.writeToFile(REVIEW_FILE, review.toFileString(), true);
        return true;
    }

    // READ all reviews
    public List<Review> getAllReviews() throws IOException {
        List<Review> reviews = new ArrayList<>();
        List<String> lines = FileUtil.readFromFile(REVIEW_FILE);
        for (String line : lines) {
            Review review = Review.fromFileString(line);
            if (review != null) reviews.add(review);
        }
        return reviews;
    }

    // READ reviews by car ID
    public List<Review> getReviewsByCar(int carId) throws IOException {
        return getAllReviews().stream()
                .filter(r -> r.getCarId() == carId)
                .collect(Collectors.toList());
    }

    // READ average rating for a car
    public double getAverageRating(int carId) throws IOException {
        List<Review> reviews = getReviewsByCar(carId);
        if (reviews.isEmpty()) return 0.0;
        double sum = reviews.stream().mapToInt(Review::getRating).sum();
        return sum / reviews.size();
    }

    // READ review by ID
    public Review getReviewById(int id) throws IOException {
        return getAllReviews().stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // DELETE review (admin only)
    public boolean deleteReview(int reviewId) throws IOException {
        List<String> lines = FileUtil.readFromFile(REVIEW_FILE);
        List<String> newLines = new ArrayList<>();
        boolean deleted = false;
        for (String line : lines) {
            Review review = Review.fromFileString(line);
            if (review != null && review.getId() == reviewId) {
                deleted = true;
            } else {
                newLines.add(line);
            }
        }
        if (deleted) {
            FileUtil.deleteFile(REVIEW_FILE);
            for (String line : newLines) {
                FileUtil.writeToFile(REVIEW_FILE, line, true);
            }
        }
        return deleted;
    }
}