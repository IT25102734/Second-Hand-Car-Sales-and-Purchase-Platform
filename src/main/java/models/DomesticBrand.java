package models;

public class DomesticBrand extends Brand {
    private int localManufacturingPlants; // extra field for domestic brands

    public DomesticBrand(int id, String name, String country) {
        super(id, name, country, "domestic");
        this.localManufacturingPlants = 1; // default
    }

    public DomesticBrand(int id, String name, String country, int plants) {
        super(id, name, country, "domestic");
        this.localManufacturingPlants = plants;
    }

    public int getLocalManufacturingPlants() { return localManufacturingPlants; }
    public void setLocalManufacturingPlants(int plants) { this.localManufacturingPlants = plants; }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + localManufacturingPlants;
    }
}