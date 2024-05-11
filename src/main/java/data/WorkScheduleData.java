package data;

public enum WorkScheduleData {
    FULL("Полный день"),
    FLEXIBLE("Гибкий график"),
    REMOTE("Удаленно");
    private final String description;

    WorkScheduleData(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
