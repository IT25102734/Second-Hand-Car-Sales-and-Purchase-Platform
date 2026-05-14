package models;

public class BuyerUser extends User {
    private int budget;
    private String preferredBrand;

    // Constructor with full parameters including isAdmin (default false for buyers)
    public BuyerUser(int id, String name, String email, String password, String phone,
                     int budget, String preferredBrand, boolean isAdmin) {
        super(id, name, email, password, phone, "buyer", isAdmin);
        this.budget = budget;
        this.preferredBrand = preferredBrand;
    }

    // Convenience constructor (isAdmin = false)
    public BuyerUser(int id, String name, String email, String password, String phone,
                     int budget, String preferredBrand) {
        this(id, name, email, password, phone, budget, preferredBrand, false);
    }

    public int getBudget() { return budget; }
    public void setBudget(int budget) { this.budget = budget; }
    public String getPreferredBrand() { return preferredBrand; }
    public void setPreferredBrand(String preferredBrand) { this.preferredBrand = preferredBrand; }

    @Override
    public String toFileString() {
        // Format: parent fields + budget + preferredBrand (no isAdmin in parent? Already included via super)
        return super.toFileString() + "," + budget + "," + preferredBrand;
    }
}