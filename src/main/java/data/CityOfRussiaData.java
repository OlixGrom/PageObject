package data;

public enum CityOfRussiaData {
    OMSK("Омск"),
    MOSСOW("Москва");
    private final String description;

    CityOfRussiaData(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
