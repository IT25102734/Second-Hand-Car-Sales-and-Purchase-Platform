package models;

public class ImportedBrand extends Brand {
    private double importDutyPercentage; // extra field for imported brands

    public ImportedBrand(int id, String name, String country) {
        super(id, name, country, "imported");
        this.importDutyPercentage = 0.0;
    }

    public ImportedBrand(int id, String name, String country, double duty) {
        super(id, name, country, "imported");
        this.importDutyPercentage = duty;
    }

    public double getImportDutyPercentage() { return importDutyPercentage; }
    public void setImportDutyPercentage(double duty) { this.importDutyPercentage = duty; }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + importDutyPercentage;
    }
}