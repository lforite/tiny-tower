package entity;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 20:27
 */
public enum FloorType {

    STORE,
    APARTMENT;

    public FloorType fromString(String string) {
        for (FloorType value : values()) {
            if (value.toString().equals(string)) {
                return value;
            }
        }
        return null;
    }
}
