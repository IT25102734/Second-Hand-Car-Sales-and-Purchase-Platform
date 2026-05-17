package utils;

import models.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final String USER_FILE = "users.txt";

    // Helper: create correct User subclass from CSV line
    private static User createUserFromLine(String line) {
        String[] parts = line.split(",");
        if (parts.length < 7) return null; // id,name,email,password,phone,userType,isAdmin
        try {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            String email = parts[2];
            String password = parts[3];
            String phone = parts[4];
            String userType = parts[5];
            boolean isAdmin = Boolean.parseBoolean(parts[6]);

            if ("buyer".equals(userType) && parts.length >= 9) {
                int budget = Integer.parseInt(parts[7]);
                String preferredBrand = parts[8];
                return new BuyerUser(id, name, email, password, phone, budget, preferredBrand, isAdmin);
            }
            else if ("seller".equals(userType) && parts.length >= 9) {
                String dealershipName = parts[7];
                int yearsInBusiness = Integer.parseInt(parts[8]);
                return new SellerUser(id, name, email, password, phone, dealershipName, yearsInBusiness, isAdmin);
            }
            else {
                // Fallback – should not happen for valid buyer/seller accounts
                return new User(id, name, email, password, phone, userType, isAdmin);
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public boolean registerUser(User user) throws IOException {
        List<String> lines = FileUtil.readFromFile(USER_FILE);
        for (String line : lines) {
            User existing = createUserFromLine(line);
            if (existing != null && existing.getEmail().equals(user.getEmail())) {
                return false;
            }
        }
        int newId = lines.size() + 1;
        user.setId(newId);
        user.setAdmin(false);
        FileUtil.writeToFile(USER_FILE, user.toFileString(), true);
        return true;
    }

    public User loginUser(String email, String password) throws IOException {
        List<String> lines = FileUtil.readFromFile(USER_FILE);
        for (String line : lines) {
            User user = createUserFromLine(line);
            if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() throws IOException {
        List<User> users = new ArrayList<>();
        List<String> lines = FileUtil.readFromFile(USER_FILE);
        for (String line : lines) {
            User user = createUserFromLine(line);
            if (user != null) users.add(user);
        }
        return users;
    }

    public User getUserById(int id) throws IOException {
        List<String> lines = FileUtil.readFromFile(USER_FILE);
        for (String line : lines) {
            User user = createUserFromLine(line);
            if (user != null && user.getId() == id) return user;
        }
        return null;
    }

    public boolean updateUser(User updatedUser) throws IOException {
        List<String> lines = FileUtil.readFromFile(USER_FILE);
        List<String> newLines = new ArrayList<>();
        boolean updated = false;
        for (String line : lines) {
            User existing = createUserFromLine(line);
            if (existing != null && existing.getId() == updatedUser.getId()) {
                newLines.add(updatedUser.toFileString());
                updated = true;
            } else {
                newLines.add(line);
            }
        }
        if (updated) {
            FileUtil.deleteFile(USER_FILE);
            for (String line : newLines) {
                FileUtil.writeToFile(USER_FILE, line, true);
            }
        }
        return updated;
    }

    public boolean deleteUser(int userId) throws IOException {
        List<String> lines = FileUtil.readFromFile(USER_FILE);
        List<String> newLines = new ArrayList<>();
        boolean deleted = false;
        for (String line : lines) {
            User user = createUserFromLine(line);
            if (user != null && user.getId() == userId) {
                deleted = true;
            } else {
                newLines.add(line);
            }
        }
        if (deleted) {
            FileUtil.deleteFile(USER_FILE);
            for (String line : newLines) {
                FileUtil.writeToFile(USER_FILE, line, true);
            }
        }
        return deleted;
    }
}