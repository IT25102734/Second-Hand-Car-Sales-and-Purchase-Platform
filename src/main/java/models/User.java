package models;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String userType; // "buyer" or "seller"
    private boolean isAdmin; // NEW FIELD

    // Full constructor
    public User(int id, String name, String email, String password, String phone, String userType, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userType = userType;
        this.isAdmin = isAdmin;
    }

    // Default constructor
    public User() {
        this.isAdmin = false;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }

    // Convert to CSV string (comma-separated, now 7 fields)
    public String toFileString() {
        return id + "," + name + "," + email + "," + password + "," + phone + "," + userType + "," + isAdmin;
    }

    // Create User object from CSV line (now 7 fields)
    public static User fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length >= 7) {
            try {
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String email = parts[2];
                String password = parts[3];
                String phone = parts[4];
                String userType = parts[5];
                boolean isAdmin = Boolean.parseBoolean(parts[6]);
                return new User(id, name, email, password, phone, userType, isAdmin);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}