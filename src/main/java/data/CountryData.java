package data;

public enum CountryData {
    RUSSIA("Россия"),
    BELARUS ("Республика Беларусь");
    private final String description;

    CountryData(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
