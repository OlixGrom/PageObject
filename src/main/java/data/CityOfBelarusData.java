package data;

public enum CityOfBelarusData {
    MINSK("Минск"),
    BREST ("Брест");
    private final String description;

    CityOfBelarusData(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
