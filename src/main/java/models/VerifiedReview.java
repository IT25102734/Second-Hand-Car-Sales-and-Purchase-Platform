package models;

public class VerifiedReview extends Review {
    private String purchaseProof;

    // Constructor with default proof
    public VerifiedReview(int id, int carId, int buyerId, int rating, String comment, String reviewDate) {
        super(id, carId, buyerId, rating, comment, reviewDate, "verified");
        this.purchaseProof = "verified_by_system";
    }

    // Constructor with custom proof (optional)
    public VerifiedReview(int id, int carId, int buyerId, int rating, String comment, String reviewDate, String proof) {
        super(id, carId, buyerId, rating, comment, reviewDate, "verified");
        this.purchaseProof = proof;
    }

    public String getPurchaseProof() {
        return purchaseProof;
    }

    public void setPurchaseProof(String purchaseProof) {
        this.purchaseProof = purchaseProof;
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + purchaseProof;
    }
}