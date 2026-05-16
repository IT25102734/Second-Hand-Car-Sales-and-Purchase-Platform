package models;

public class Brand {
    private int id;
    private String name;
    private String country;
    private String type; // "domestic" or "imported"

    public Brand(int id, String name, String country, String type) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.type = type;
    }

    public Brand() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    // Convert to pipe-separated string
    public String toFileString() {
        return id + "|" + name + "|" + country + "|" + type;
    }

    // Factory method from pipe-separated line
    public static Brand fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 4) return null;
        try {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            String country = parts[2];
            String type = parts[3];
            if ("domestic".equals(type)) {
                return new DomesticBrand(id, name, country);
            } else if ("imported".equals(type)) {
                return new ImportedBrand(id, name, country);
            } else {
                return new Brand(id, name, country, type);
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }
}