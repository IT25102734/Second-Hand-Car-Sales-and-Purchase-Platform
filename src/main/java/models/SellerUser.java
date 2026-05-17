package models;

public class SellerUser extends User {
    private String dealershipName;
    private int yearsInBusiness;

    // Constructor with full parameters including isAdmin (default false for sellers)
    public SellerUser(int id, String name, String email, String password, String phone,
                      String dealershipName, int yearsInBusiness, boolean isAdmin) {
        super(id, name, email, password, phone, "seller", isAdmin);
        this.dealershipName = dealershipName;
        this.yearsInBusiness = yearsInBusiness;
    }

    // Convenience constructor (isAdmin = false)
    public SellerUser(int id, String name, String email, String password, String phone,
                      String dealershipName, int yearsInBusiness) {
        this(id, name, email, password, phone, dealershipName, yearsInBusiness, false);
    }

    public String getDealershipName() { return dealershipName; }
    public void setDealershipName(String dealershipName) { this.dealershipName = dealershipName; }
    public int getYearsInBusiness() { return yearsInBusiness; }
    public void setYearsInBusiness(int yearsInBusiness) { this.yearsInBusiness = yearsInBusiness; }

    @Override
    public String toFileString() {
        // Format: parent fields + dealershipName + yearsInBusiness
        return super.toFileString() + "," + dealershipName + "," + yearsInBusiness;
    }
}