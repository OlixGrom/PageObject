package data;

public enum LanguageLevelData {
    BEGINNER("Начальный уровень (Beginner)"),
    ELEMENTARY("Элементарный уровень (Elementary)"),
    PREINTERMEDIATE("Ниже среднего (Pre-Intermediate)"),
    INTERMEDIATE("Средний (Intermediate)"),
    UPPERINTERMEDIATE("Выше среднего (Upper Intermediate)"),
    ADVANCED("Продвинутый (Advanced)"),
    MASTERY ("Супер продвинутый (Mastery)");
    private final String description;

    LanguageLevelData(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
