package models;

public class PublicReview extends Review {
    private boolean isAnonymous;

    // Constructor without anonymous (default false)
    public PublicReview(int id, int carId, int buyerId, int rating, String comment, String reviewDate) {
        super(id, carId, buyerId, rating, comment, reviewDate, "public");
        this.isAnonymous = false;
    }

    // Constructor with anonymous flag
    public PublicReview(int id, int carId, int buyerId, int rating, String comment, String reviewDate, boolean anonymous) {
        super(id, carId, buyerId, rating, comment, reviewDate, "public");
        this.isAnonymous = anonymous;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + isAnonymous;
    }
}