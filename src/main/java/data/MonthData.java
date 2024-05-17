package data;

public enum MonthData {
    JAN("Jan", "1"),
    FEB("Feb", "2"),
    MAR("Mar", "3"),
    APR("Apr", "4"),
    MAY("May", "5"),
    JUN("Jun", "6"),
    JUL("Jul", "7"),
    AUG("Aug", "8"),
    SEP("Sep", "9"),
    OCT("Oct", "10"),
    NOV("Nov", "11"),
    DEC("Dec", "12");
    private final String description;
    private final String symbol;

    MonthData(String description, String symbol) {
        this.description = description;
        this.symbol = symbol;
    }

    public static String getDescriptionBySymbol(String symbol) {
        MonthData[] values = MonthData.values();
        String enumValue = null;
        for (MonthData eachValue : values) {
            enumValue = eachValue.getSymbol();

            if (enumValue.equalsIgnoreCase(symbol)) {
                return eachValue.getDescription();
            }
        }
        return enumValue;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }

}
