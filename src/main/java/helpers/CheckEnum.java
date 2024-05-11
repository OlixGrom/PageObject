package helpers;

public class CheckEnum {
    public <T extends Enum<T>> boolean checkValueInEnum(Class<T> enumType, String value) {
        for (T val : enumType.getEnumConstants()) {
            if(val.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
