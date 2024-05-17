package data;

public enum GenderData {
    MALE("Мужской", "m"),
    FEMALE("Женский", "f");

    private final String description;
    private final String symbol;

    GenderData(String description, String symbol) {
        this.description = description;
        this.symbol = symbol;
    }

    public static String getSymbolByGender(GenderData gender) {
        return gender.getSymbol();
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }
}
